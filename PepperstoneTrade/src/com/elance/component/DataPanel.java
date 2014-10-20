package com.elance.component;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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
        	panel = makeTextPanel("Data of "+account);
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
    
    protected JPanel makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
    
}
