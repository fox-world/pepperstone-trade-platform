package com.elance.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.elance.nj4x.MT4ConnectionUtil;
import com.elance.util.OrderUtil;
import com.elance.util.constants.OrderAction;
import com.elance.vo.AccountConfig;
import com.elance.vo.AccountVO;
import com.elance.vo.ButtonStatusVO;
import com.jfx.TradeOperation;
import com.jfx.strategy.OrderInfo;

public class OrderListener implements ActionListener {

    private OrderAction orderAction;
    private List<AccountVO> accountList;
    private final AccountConfig accountConfig;
    private ButtonStatusVO buttonStatusVO;

    public OrderListener(OrderAction orderAction, List<AccountVO> accountList, AccountConfig accountConfig, ButtonStatusVO buttonStatusVO) {
        this.orderAction = orderAction;
        this.accountList = accountList;
        this.accountConfig = accountConfig;
        this.buttonStatusVO = buttonStatusVO;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        synchronized (accountConfig) {
            ExecutorService threadsPool = accountConfig.executorService;
            List<Future<?>> jobs = new ArrayList<>();
            for (final AccountVO accountVO : accountList) {
                System.out.println("=========accountVO:\t" + accountVO.getAccountText().getText() + "\t" + orderAction);
                if (accountVO.getTotalLotsForNextTrade() == 0.0) {
                    continue;
                }
                Future<?> future = threadsPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        String maxLotsStr = accountConfig.getMaxLotsSpinner().getValue().toString();
                        MT4ConnectionUtil mt4Util = accountVO.getMt4ConnectionUtil();
                        String symbol = mt4Util.getSymbol();

                        double[] lots = OrderUtil.calculateOrderLots(accountVO.getTotalLotsForNextTrade(), Double.valueOf(maxLotsStr));
                        int maxTradeLots = Integer.parseInt(accountConfig.getMaxTradesSpinner().getValue().toString());
                        int num = 0;
                        if (orderAction == OrderAction.CLOSE) {
                            buttonStatusVO.setCloseOrder(true);
                            closeAccountOrders(accountVO, maxTradeLots);
                            buttonStatusVO.setCloseOrder(false);
                        } else {
                            for (double tradeLot : lots) {

                                num++;
                                if (num > maxTradeLots) {
                                    continue;
                                }

                                if (orderAction == OrderAction.OPEN_BUY) {
                                    mt4Util.orderSendAsynchronously(symbol, TradeOperation.OP_BUY, tradeLot, 0/*managed by MT4ConnectionUtil*/, 10, 0, 0, null, 0, null);
                                } else if (orderAction == OrderAction.OPEN_SELL) {
                                    mt4Util.orderSendAsynchronously(symbol, TradeOperation.OP_SELL, tradeLot, 0/*managed by MT4ConnectionUtil*/, 10, 0, 0, null, 0, null);
                                }
                            }
                            mt4Util.waitForAllJobsToComplete();
                            mt4Util.loadOrders();
                        }
                    }
                });
                jobs.add(future);
            }
            //
            waitForJobsToComplete(jobs);
        }
    }

    private void waitForJobsToComplete(List<Future<?>> jobs) {
        int doneCount = 0;
        while (doneCount < jobs.size()) {
            doneCount = 0;
            for (Future<?> f : jobs) {
                if (f.isDone() || f.isCancelled()) {
                    try {
                        f.get(); // can generate exception
                    } catch (Exception e1) {
                        e1.printStackTrace(); // print error happened during job execution
                    }
                    doneCount++;
                }
            }
        }
    }

    public void closeAccountOrders(AccountVO accountVO, int maxTradeLots) {
        long startTime = System.currentTimeMillis();
        MT4ConnectionUtil mt4Util = accountVO.getMt4ConnectionUtil();

        ArrayList<OrderInfo> allOrders = mt4Util.getOrders(); // use cache
        List<OrderInfo> orderList = new ArrayList<OrderInfo>();
        for (OrderInfo orderInfo : allOrders) {
            if (orderInfo.getType() == TradeOperation.OP_BUY || orderInfo.getType() == TradeOperation.OP_SELL) {
                orderList.add(orderInfo);
            }
        }
        for (OrderInfo order : orderList) {
            mt4Util.orderCloseAsynchronously(order, order.getLots(), 0/*managed by MT4ConnectionUtil*/, 10);
        }
        long scheduledTime = System.currentTimeMillis();
        mt4Util.waitForAllJobsToComplete();
        long completeTime = System.currentTimeMillis();
        System.out.printf("Close jobs have been scheduled in %d millis and completed in %d millis\n", scheduledTime - startTime, completeTime - startTime);
        mt4Util.loadOrders();
    }

}
