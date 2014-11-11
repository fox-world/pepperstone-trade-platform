package com.elance.nj4x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.jfx.ErrUnknownSymbol;
import com.jfx.MT4;
import com.jfx.SelectionPool;
import com.jfx.SelectionType;
import com.jfx.Tick;
import com.jfx.TradeOperation;
import com.jfx.net.JFXServer;
import com.jfx.strategy.OrderInfo;
import com.jfx.strategy.Strategy;
import com.jfx.strategy.StrategyRunner;

public class MT4ConnectionUtil extends Strategy {
    
    private final ExecutorService threadPool;
    private final List<Future<Long>> jobs;
    int accountOrderWorkers = 3;
    private ConcurrentHashMap<String, Tick> ticks;
    
    private ArrayList<OrderInfo> orders = new ArrayList<>();

    public MT4ConnectionUtil() {
        threadPool = Executors.newFixedThreadPool(accountOrderWorkers);
        jobs = new ArrayList<Future<Long>>();
    }

    public ArrayList<OrderInfo> getOrders() {
        return orders;
    }
    

    public boolean connect(String username, String password) throws Exception {
        System.out.println("======= Connecting ========= " + JFXServer.getInstance().getBindPort());
        ticks = new ConcurrentHashMap<String,Tick>();
        setBulkTickListener(new BulkTickListener() {
            @Override
            public void onTicks(List<Tick> list, MT4 mt4) {
                for (Tick t : list) {
                    ticks.put(t.symbol, t);
                }
            }
        });
        connect("127.0.0.1", 7788, BrokerConfig.PAPER_SERVER, username, password);
        addShutdownHook();
        connectOrderWorkers();
        return true;
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    MT4ConnectionUtil.this.close(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void connectOrderWorkers() throws IOException {
        for (int i = 0; i < accountOrderWorkers; i++) {
            addTerminal(TerminalType.ORDERS_WORKER).connect();
        }
    }

    @Override
    public void init(String symbol, int period, StrategyRunner strategyRunner) throws ErrUnknownSymbol, IOException {
        loadOrders();
    }

    public void loadOrders() {
        waitForAllJobsToComplete();
        ArrayList<OrderInfo> ordersSnapshot = new ArrayList<>();
        int availableOrdersCount = ordersTotal();
        for (int i = 0; i < availableOrdersCount; i++) {
            OrderInfo o = orderGet(i, SelectionType.SELECT_BY_POS, SelectionPool.MODE_TRADES);
            if (o != null) {
                ordersSnapshot.add(o);
            }
        }
        orders = ordersSnapshot;
    }

    public Future<Long> orderSendAsynchronously(final String symbol, final TradeOperation cmd, final double volume, final double price, 
    		final int slippage, final double stoploss, final double takeprofit, final String comment, final int magic, final Date expiration) {
        Future<Long> future = threadPool.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                double _price = price;
                if (_price == 0) {
                    Tick tick = ticks.get(symbol);
                    if (tick == null) {
                        // no price
                        return 0L;
                    }
                    _price = cmd == TradeOperation.OP_BUY ? tick.ask : tick.bid;
                }
                return orderSend(symbol, cmd, volume, _price, slippage, stoploss, takeprofit, comment, magic, expiration);
            }
        });
        synchronized (jobs) {
            jobs.add(future);
        }
        return future;
    }

    public Future<Long> orderCloseAsynchronously(final OrderInfo orderInfo, final double lots, final double price, final int slippage) {
        Future<Long> future = threadPool.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                double _price = price;
                if (_price == 0) {
                    Tick tick = ticks.get(orderInfo.getSymbol());
                    if (tick == null) {
                        // no price
                        return 0L;
                    }
                    _price = orderInfo.getType() == TradeOperation.OP_BUY ? tick.bid : tick.ask;
                }
                boolean isClosed = orderClose(orderInfo.ticket(), lots, _price, slippage, 0);
                return isClosed ? 1L : 0L;
            }
        });
        synchronized (jobs) {
            jobs.add(future);
        }
        return future;
    }

    public void waitForAllJobsToComplete() {
        ArrayList<Exception> errors = new ArrayList<>();
        synchronized (jobs) {
            while (jobs.size() > 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignore) {
                }
                ArrayList<Future<Long>> done = new ArrayList<>();
                ArrayList<Future<Long>> erroneous = new ArrayList<>();
                errors.clear();
                for (Future<Long> f : jobs) {
                    if (f.isDone() || f.isCancelled()) {
                        try {
                            f.get();
                            done.add(f);
                        } catch (Exception e) {
                            erroneous.add(f);
                            errors.add(e);
                        }
                    }
                }
                for (Future<Long> f : done) {
                    jobs.remove(f);
                }
                for (Future<Long> f : erroneous) {
                    jobs.remove(f);
                }
            }
        }
        //
        for (Exception e: errors) {
            System.out.println("Error: " + e);
        }
    }
}
