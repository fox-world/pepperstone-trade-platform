package com.elance.component;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.elance.util.constants.ComponentConstants;
import com.elance.vo.AccountVO;

public class DataPanel extends JPanel {

	private static final long serialVersionUID = -8610183526601441962L;
	
	private JPanel tabPanel;
	private JPanel buttonPanel;
	
	private List<AccountVO> accountList;
	
	public DataPanel(){
	}
	
	public DataPanel(List<AccountVO> accountList){
		this.accountList=accountList;
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
        	panel = makeTextPanel(accountVO);
            tabbedPane.addTab(account, null, panel,"Data of "+account);
            if(accountVO.isLoginResult()){
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
        buttonPanel.add(openBuyButton);
        
        JButton openSellButton=new JButton("Open Sell");
        openSellButton.setBounds(110, 5, 100, 25);
        buttonPanel.add(openSellButton);
        
        JButton closeButton=new JButton("Close");
        closeButton.setBounds(218, 5, 100, 25);
        buttonPanel.add(closeButton);
        
        JButton hedgeButton=new JButton("Hedge");
        hedgeButton.setBounds(325, 5, 100, 25);
        buttonPanel.add(hedgeButton);
        
        JButton closeHedgeButton=new JButton("Close Hedge");
        closeHedgeButton.setBounds(432, 5, 120, 25);
        buttonPanel.add(closeHedgeButton);
        
        JButton loginAgainButton=new JButton("Close Hedge");
        loginAgainButton.setBounds(677, 5, 120, 25);
        buttonPanel.add(loginAgainButton);
        
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
    
    protected JPanel makeTextPanel(AccountVO accountVO) {
    	
    	JPanel panel = new JPanel(false);
    	
    	boolean loginSuccess=accountVO.isLoginResult();
    	String tabContent=null;
    	
    	if(loginSuccess){
    
    		tabContent="Data of "+accountVO.getAccountText().getText();
    		panel.setLayout(null);
    		
    		JLabel serverConnectTimeLabel=new JLabel("Server connect time: ");
        	JLabel serverConnectTimeValue = new JLabel(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accountVO.getConnectTime()));
        	serverConnectTimeLabel.setBounds(20, 10, 150,ComponentConstants.COMPONENT_HEIGHT);
        	serverConnectTimeValue.setBounds(150,10, ComponentConstants.COMPONENT_LABEL_WIDTH_MEDIUM,ComponentConstants.COMPONENT_HEIGHT);
        	panel.add(serverConnectTimeLabel);
        	panel.add(serverConnectTimeValue);
        	
        	JLabel currencyEquityLabel=new JLabel("Currency equity: ");
        	JLabel currencyEquityValue = new JLabel(String.valueOf(accountVO.getMt4ConnectionUtil().accountEquity()));
        	currencyEquityLabel.setBounds(47, 30, 130,ComponentConstants.COMPONENT_HEIGHT);
        	currencyEquityValue.setBounds(150,30, ComponentConstants.COMPONENT_LABEL_WIDTH_MEDIUM,ComponentConstants.COMPONENT_HEIGHT);
        	panel.add(currencyEquityLabel);
        	panel.add(currencyEquityValue);
        	
    		JLabel accountBalanceLabel=new JLabel("Account Balance: ");
    		JLabel accountBalanceValue = new JLabel(String.valueOf(accountVO.getMt4ConnectionUtil().accountBalance()));
    		accountBalanceLabel.setBounds(40, 50, 130,ComponentConstants.COMPONENT_HEIGHT);
    		accountBalanceValue.setBounds(150,50, ComponentConstants.COMPONENT_LABEL_WIDTH_MEDIUM,ComponentConstants.COMPONENT_HEIGHT);
    		panel.add(accountBalanceLabel);
    		panel.add(accountBalanceValue);
    		
    		JLabel lotSizeForNextTradeLabel=new JLabel("Lot size for next trade:");
    		JLabel lotSizeForNextTradeValue = new JLabel("");
    		lotSizeForNextTradeLabel.setBounds(10, 70, 150,ComponentConstants.COMPONENT_HEIGHT);
    		lotSizeForNextTradeValue.setBounds(150,70, ComponentConstants.COMPONENT_LABEL_WIDTH_MEDIUM,ComponentConstants.COMPONENT_HEIGHT);
    		panel.add(lotSizeForNextTradeLabel);
    		panel.add(lotSizeForNextTradeValue);
    		
    		JLabel openTradeLotsLabel=new JLabel("Open trade lots:");
    		JLabel openTradeLotsValue = new JLabel("");
    		openTradeLotsLabel.setBounds(47, 90, 150,ComponentConstants.COMPONENT_HEIGHT);
    		openTradeLotsValue.setBounds(150,90, ComponentConstants.COMPONENT_LABEL_WIDTH_MEDIUM,ComponentConstants.COMPONENT_HEIGHT);
    		panel.add(openTradeLotsLabel);
    		panel.add(openTradeLotsValue);
    		
    	}else{
    		tabContent=accountVO.getErrorMessage();
    		panel.setLayout(new GridLayout(1, 1));
            JLabel filler = new JLabel(tabContent);
            filler.setHorizontalAlignment(JLabel.CENTER);
            panel.add(filler);
    	}
        return panel;
    }
    
}
