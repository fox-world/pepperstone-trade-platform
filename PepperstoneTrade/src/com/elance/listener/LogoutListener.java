package com.elance.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;

import com.elance.component.DataPanel;
import com.elance.component.LoginPanel;
import com.elance.vo.AccountConfig;
import com.elance.vo.AccountVO;

public class LogoutListener implements ActionListener {
	
	private JFrame frame;
	private DataPanel dataPanel;
    private List<AccountVO> accountList;
    private AccountConfig accountConfig;
    private boolean loginAll;

    public LogoutListener(JFrame frame,DataPanel dataPanel,List<AccountVO> accountList,AccountConfig accountConfig,boolean loginAll){
    	this.frame=frame;
    	this.dataPanel=dataPanel;
    	this.accountList=accountList;
    	this.accountConfig=accountConfig;
    	this.loginAll=loginAll;
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
        int availableAccountCount=Integer.parseInt(accountConfig.getServerNumberSpinner().getValue().toString());
        AccountVO accountVO=null;
        LoginPanel loginPanel=null;
        try {
			for(int i=0;i<availableAccountCount;i++){
				accountVO=accountList.get(i);
				if(loginAll&&accountVO.isLoginSuccess()){
					accountVO.getMt4ConnectionUtil().close();
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
        frame.getContentPane().remove(dataPanel);
        if(loginAll){
        	loginPanel=new LoginPanel(frame);
        }else{
            loginPanel=new LoginPanel(frame,accountList);
        }
        loginPanel.showLoginPanel(loginAll);
        frame.getContentPane().add(loginPanel);
        frame.revalidate();
	}

}
