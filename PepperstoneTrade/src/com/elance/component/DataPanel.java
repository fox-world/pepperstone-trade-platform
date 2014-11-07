package com.elance.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.elance.listener.OrderListener;
import com.elance.nj4x.MT4ConnectionUtil;
import com.elance.util.OrderUtil;
import com.elance.util.constants.ComponentConstants;
import com.elance.util.constants.OrderAction;
import com.elance.vo.AccountConfig;
import com.elance.vo.AccountVO;
import com.elance.vo.ButtonStatusVO;
import com.jfx.SelectionPool;
import com.jfx.SelectionType;
import com.jfx.strategy.OrderInfo;

public class DataPanel extends JPanel {

	private static final long serialVersionUID = -8610183526601441962L;
	
	private JPanel tabPanel;
	private JPanel buttonPanel;
	
	private List<AccountVO> accountList;
	private AccountConfig accountConfig;
	private ButtonStatusVO buttonStatusVO;
	
	public DataPanel(){
	}
	
	public DataPanel(List<AccountVO> accountList,AccountConfig accountConfig){
		this.accountList=accountList;
		this.accountConfig=accountConfig;
		buttonStatusVO=new ButtonStatusVO();
	}

	public void initTabPanel() {
		
        
        tabPanel=new JPanel();
        buttonPanel=new JPanel();
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
		c.ipadx=800;
		c.ipady=600;
		tabPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"Accounts information"));
		add(tabPanel,c);
		c.gridx=0;
		c.gridy=1;
		c.ipady=40;
		add(buttonPanel,c);
        
        tabPanel.setLayout(null);
        buttonPanel.setLayout(null);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(2, 20,795, 575);
        
        int index=0;
        String account=null;
        JPanel panel=null;
        for(AccountVO accountVO:accountList){
            account=accountVO.getAccountText().getText();
        	panel = makeTextPanel(accountVO,accountConfig);
            tabbedPane.addTab(account, null, panel,"Data of "+account);
            if(accountVO.isLoginSuccess()){
            	tabbedPane.setForegroundAt(index,Color.BLUE);
            }else{
            	tabbedPane.setForegroundAt(index,Color.RED);
            }
            index++;
        }      
        
        //Add the tabbed pane to this panel.
        tabPanel.add(tabbedPane);
        
        JButton openBuyButton=new JButton("Open Buy");
        openBuyButton.setBounds(2, 5, 100, 25);
        openBuyButton.addActionListener(new OrderListener(OrderAction.OPEN_BUY,accountList,accountConfig,buttonStatusVO));
        buttonPanel.add(openBuyButton);
        
        JButton openSellButton=new JButton("Open Sell");
        openSellButton.setBounds(110, 5, 100, 25);
        openSellButton.addActionListener(new OrderListener(OrderAction.OPEN_SELL,accountList,accountConfig,buttonStatusVO));
        buttonPanel.add(openSellButton);
        
        JButton closeButton=new JButton("Close");
        closeButton.setBounds(218, 5, 100, 25);
        closeButton.addActionListener(new OrderListener(OrderAction.CLOSE,accountList,accountConfig,buttonStatusVO));
        buttonPanel.add(closeButton);
        
        JButton hedgeButton=new JButton("Hedge");
        hedgeButton.setBounds(325, 5, 100, 25);
        buttonPanel.add(hedgeButton);
        
        JButton closeHedgeButton=new JButton("Close Hedge");
        closeHedgeButton.setBounds(432, 5, 120, 25);
        buttonPanel.add(closeHedgeButton);
        
        JButton loginAgainButton=new JButton("Login Again");
        loginAgainButton.setBounds(677, 5, 120, 25);
        buttonPanel.add(loginAgainButton);
        
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); 
        
        updateTabContent(tabbedPane,accountList,accountConfig,buttonStatusVO);
    }
    
    protected JPanel makeTextPanel(AccountVO accountVO,AccountConfig accountConfig) {
    	
    	JPanel panel = new JPanel(false);
    	
    	boolean loginSuccess=accountVO.isLoginSuccess();
    	String tabContent=null;
    	
    	if(loginSuccess){
    
    		MT4ConnectionUtil mt4Util=accountVO.getMt4ConnectionUtil();
    		
    		tabContent="Data of "+accountVO.getAccountText().getText();
    		panel.setLayout(null);
    		
    		JLabel serverConnectTimeLabel=new JLabel("Server connect time: ");//1
        	JLabel serverConnectTimeValue = new JLabel(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accountVO.getConnectTime()));//2
        	serverConnectTimeLabel.setBounds(20, 10, 150,ComponentConstants.COMPONENT_HEIGHT);
        	serverConnectTimeValue.setBounds(150,10, ComponentConstants.COMPONENT_LABEL_WIDTH_MEDIUM,ComponentConstants.COMPONENT_HEIGHT);
        	panel.add(serverConnectTimeLabel);
        	panel.add(serverConnectTimeValue);
        	
        	double accountEquity=mt4Util.accountEquity();
        	JLabel currencyEquityLabel=new JLabel("Currency equity: ");//3
        	JLabel currencyEquityValue = new JLabel(String.format("%.2f",accountEquity));//4
        	currencyEquityLabel.setBounds(47, 30, 130,ComponentConstants.COMPONENT_HEIGHT);
        	currencyEquityValue.setBounds(150,30, ComponentConstants.COMPONENT_LABEL_WIDTH_MEDIUM,ComponentConstants.COMPONENT_HEIGHT);
        	panel.add(currencyEquityLabel);
        	panel.add(currencyEquityValue);
        	
    		JLabel accountBalanceLabel=new JLabel("Account Balance: ");//5
    		JLabel accountBalanceValue = new JLabel(String.format("%.2f",mt4Util.accountBalance()));//6
    		accountBalanceLabel.setBounds(40, 50, 130,ComponentConstants.COMPONENT_HEIGHT);
    		accountBalanceValue.setBounds(150,50, ComponentConstants.COMPONENT_LABEL_WIDTH_MEDIUM,ComponentConstants.COMPONENT_HEIGHT);
    		panel.add(accountBalanceLabel);
    		panel.add(accountBalanceValue);
    		
    		double totalLotsForNextTrade=accountEquity/(1000/Double.parseDouble(accountConfig.getLotSizeText().getText()));
    		totalLotsForNextTrade=new BigDecimal(totalLotsForNextTrade).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    		JLabel totalLotsForNextTradeLabel=new JLabel("Total lots next trade:");//7
    		JLabel totalLotsForNextTradeValue = new JLabel(String.format("%.2f",totalLotsForNextTrade));//8
     		accountVO.setTotalLotsForNextTrade(totalLotsForNextTrade);
    		totalLotsForNextTradeLabel.setBounds(20, 70, 150,ComponentConstants.COMPONENT_HEIGHT);
    		totalLotsForNextTradeValue.setBounds(150,70, ComponentConstants.COMPONENT_LABEL_WIDTH_MEDIUM,ComponentConstants.COMPONENT_HEIGHT);
    		panel.add(totalLotsForNextTradeLabel);
    		panel.add(totalLotsForNextTradeValue);
    		
    		
    		JLabel openTradeLotsLabel=new JLabel("Open trade lots:");//9
    		JLabel openTradeLotsValue = new JLabel("");//10
    		openTradeLotsLabel.setBounds(47, 90, 150,ComponentConstants.COMPONENT_HEIGHT);
    		openTradeLotsValue.setBounds(150,90, ComponentConstants.COMPONENT_LABEL_WIDTH_MEDIUM,ComponentConstants.COMPONENT_HEIGHT);
    		panel.add(openTradeLotsLabel);
    		panel.add(openTradeLotsValue);
    		
    		JLabel currencyPairsLabel=new JLabel("Currency paris:");//10
    		currencyPairsLabel.setBounds(50,110, 130, ComponentConstants.COMPONENT_HEIGHT);
    		List<String> currencyPairList=mt4Util.getSymbols();
    	    String[] currencyPairs=currencyPairList.toArray(new String[currencyPairList.size()]);
    		JComboBox<String> currencyPairsCombox=new JComboBox<String>(currencyPairs);//11
    		currencyPairsCombox.setSelectedItem(mt4Util.getSymbol());
    	    currencyPairsCombox.setBounds(150, 110, 150, ComponentConstants.COMPONENT_HEIGHT);
    	    panel.add(currencyPairsCombox);
    	    panel.add(currencyPairsLabel);
    		
    	    JPanel tablePanel=new JPanel();//12
    	    String[] columnNames={"","Order","Time","Type","Size","Symbol","Price","Commission","Swap","Profit"};
    	    DateFormat format=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    		int availableOrdersCount=mt4Util.ordersTotal();
    		Object[][] cells=new Object[availableOrdersCount][10];
    		OrderInfo orderInfo=null;
    		double totalLots=0;
    		double lots=0;
    		for(int i=0;i<availableOrdersCount;i++){
    			orderInfo =mt4Util.orderGet(i, SelectionType.SELECT_BY_POS, SelectionPool.MODE_TRADES);
    			cells[i][0]=i+1;
    			cells[i][1]=orderInfo.ticket();
    			cells[i][2]=format.format(orderInfo.getOpenTime());
    			cells[i][3]=orderInfo.getType();
    			lots=orderInfo.getLots();
    			totalLots+=lots;
    			cells[i][4]=lots;
    			cells[i][5]=orderInfo.getSymbol();
    			cells[i][6]=orderInfo.getOpenPrice();
    			cells[i][7]=orderInfo.getCommission();
    			cells[i][8]=orderInfo.getSwap();
    			cells[i][9]=orderInfo.getProfit();
    			
    		}
    		OrderUtil.quickSort(cells, 0, availableOrdersCount-1);
    		openTradeLotsValue.setText(String.format("%.2f",totalLots));
    		JTable jTable=new JTable(cells,columnNames);
    		jTable.setPreferredScrollableViewportSize(new Dimension(750, 360));
    		this.setJTableColumnWidth(jTable);
    		JScrollPane sPane=new JScrollPane(jTable);
    		tablePanel.setBounds(5, 135, 780, 410);
    		tablePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"Orders List"));
    		tablePanel.add(sPane);
    		panel.add(tablePanel);
    		
    		JLabel profiltLabel=new JLabel("Account Profit: ");//13
    		JLabel profitValue = new JLabel(String.format("%.2f",mt4Util.accountProfit()));//14
    		profiltLabel.setBounds(410, 10, 150,ComponentConstants.COMPONENT_HEIGHT);
    		profitValue.setBounds(500,10, 150,ComponentConstants.COMPONENT_HEIGHT);
    		panel.add(profiltLabel);
    		panel.add(profitValue);
    		
    		JLabel marginLabel=new JLabel("Margin: ");//15
        	JLabel marginValue = new JLabel(String.format("%.2f",mt4Util.accountMargin()));//16
        	marginLabel.setBounds(452, 30, 150,ComponentConstants.COMPONENT_HEIGHT);
        	marginValue.setBounds(500,30, 150,ComponentConstants.COMPONENT_HEIGHT);
        	panel.add(marginLabel);
        	panel.add(marginValue);
        	
        	JLabel freeMarginLabel=new JLabel("Free Margin: ");//17
        	JLabel freeMarginValue = new JLabel(String.format("%.2f",mt4Util.accountFreeMargin()));//18
        	freeMarginLabel.setBounds(425, 50, 150,ComponentConstants.COMPONENT_HEIGHT);
        	freeMarginValue.setBounds(500,50, 150,ComponentConstants.COMPONENT_HEIGHT);
        	panel.add(freeMarginLabel);
        	panel.add(freeMarginValue);
    	}else{
    		tabContent=accountVO.getErrorMessage();
    		panel.setLayout(new GridLayout(1, 1));
            JLabel filler = new JLabel(tabContent);
            filler.setHorizontalAlignment(JLabel.CENTER);
            panel.add(filler);
    	}
        return panel;
    }
    
    public void updateTabContent(JTabbedPane tabbedPane,List<AccountVO> accountList,AccountConfig accountConfig,ButtonStatusVO buttonStatusVO){
    	try {
    		int selectedIndex=0;
    		AccountVO accountVO=null;
    		while(true){
    			if(!buttonStatusVO.needStopUpdate()){
    				selectedIndex=tabbedPane.getSelectedIndex();
        			accountVO=accountList.get(selectedIndex);
        			if(accountVO.isLoginSuccess()){
        	  			JPanel panel=(JPanel) tabbedPane.getSelectedComponent();
            			updateTabeContent(panel, accountVO,accountConfig);	
        			}
    			}
    			Thread.sleep(1000);	
    		}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    public void updateTabeContent(JPanel panel,AccountVO accountVO,AccountConfig accountConfig){
    	
    	MT4ConnectionUtil mt4Util=accountVO.getMt4ConnectionUtil();
    	
    	JLabel accountEquitylabel=(JLabel) panel.getComponent(3);
		double accountEquity=mt4Util.accountEquity();
		accountEquitylabel.setText(String.format("%.2f",accountEquity));
		
		JLabel accountBalancelabel=(JLabel) panel.getComponent(5);
		accountBalancelabel.setText(String.format("%.2f",mt4Util.accountBalance()));
		
		double totalLotsForNextTrade=accountEquity/(1000/Double.parseDouble(accountConfig.getLotSizeText().getText()));
		totalLotsForNextTrade=new BigDecimal(totalLotsForNextTrade).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		accountVO.setTotalLotsForNextTrade(totalLotsForNextTrade);
		JLabel totalLotsForNextTradeLabel=(JLabel) panel.getComponent(7);
		totalLotsForNextTradeLabel.setText(String.format("%.2f",totalLotsForNextTrade));
		
		JLabel accountProfitlabel=(JLabel) panel.getComponent(14);
		accountProfitlabel.setText(String.format("%.2f",mt4Util.accountProfit()));
		
		JLabel accountMarginlabel=(JLabel) panel.getComponent(16);
		accountMarginlabel.setText(String.format("%.2f",mt4Util.accountMargin()));
		
		JLabel accountFreeMarginlabel=(JLabel) panel.getComponent(18);
		accountFreeMarginlabel.setText(String.format("%.2f",mt4Util.accountFreeMargin()));
		
		JPanel tablePanel=(JPanel) panel.getComponent(12);
		 String[] columnNames={"","Order","Time","Type","Size","Symbol","Price","Commission","Swap","Profit"};
 	    DateFormat format=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
 		int availableOrdersCount=mt4Util.ordersTotal();
 		Object[][] cells=new Object[availableOrdersCount][10];
 		OrderInfo orderInfo=null;
 		double totalLots=0;
		double lots=0;
 		for(int i=0;i<availableOrdersCount;i++){
 			orderInfo =mt4Util.orderGet(i, SelectionType.SELECT_BY_POS, SelectionPool.MODE_TRADES);
 			cells[i][0]=i+1;
 			cells[i][1]=orderInfo.ticket();
 			cells[i][2]=format.format(orderInfo.getOpenTime());
 			cells[i][3]=orderInfo.getType();
 			lots=orderInfo.getLots();
 			totalLots+=lots;
 			cells[i][4]=lots;
 			cells[i][5]=orderInfo.getSymbol();
 			cells[i][6]=orderInfo.getOpenPrice();
 			cells[i][7]=orderInfo.getCommission();
 			cells[i][8]=orderInfo.getSwap();
 			cells[i][9]=orderInfo.getProfit();
 			
 		}
 		OrderUtil.quickSort(cells, 0, availableOrdersCount-1);
 		JTable jTable=new JTable(cells,columnNames);
 		jTable.setPreferredScrollableViewportSize(new Dimension(750, 360));
 		this.setJTableColumnWidth(jTable);
 		JScrollPane sPane=new JScrollPane(jTable);
 		tablePanel.remove(0);
 		tablePanel.add(sPane);
 		
 		JLabel openTradeLotsLabel=(JLabel) panel.getComponent(9);
 		openTradeLotsLabel.setText(String.format("%.2f",totalLots));
		
    }
    
    public void setJTableColumnWidth(JTable jTable){
    	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    	centerRenderer.setHorizontalAlignment( JLabel.CENTER);
		DefaultTableCellRenderer rightRenderer=new DataTableCellRender();
		
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		jTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		jTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		jTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		jTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		jTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		jTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		jTable.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		jTable.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
		jTable.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
		jTable.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
		
		jTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		jTable.getColumnModel().getColumn(1).setPreferredWidth(60);
		jTable.getColumnModel().getColumn(2).setPreferredWidth(140);
		jTable.getColumnModel().getColumn(3).setPreferredWidth(45);
		jTable.getColumnModel().getColumn(9).setPreferredWidth(100);
    }
    
}
