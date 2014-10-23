package com.elance.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.elance.component.DataPanel;
import com.elance.component.LoginPanel;
import com.elance.vo.AccountVO;

public class LoginListener implements ActionListener {
	
	private JFrame frame;
	private LoginPanel loginPanel;
	private List<AccountVO> accountList;
	
	private JLabel processingLabel;
	private boolean loginProcessFinished;
	private int loginFinishCount;
	private boolean loginResult;
	
    public LoginListener(){
    }
    
    public LoginListener(JFrame frame,LoginPanel loginPanel,JLabel processingLabel,List<AccountVO> accountList){
    	this.frame=frame;
    	this.loginPanel=loginPanel;
    	this.accountList=accountList;
    	this.processingLabel=processingLabel;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		loginProcessFinished=false;
		loginFinishCount=0;
		loginResult=false;
		
	    processingLabel.setVisible(true);
	    loginPanel.changeComponentsEnableStatus(false);
	    
		 new Thread(new Runnable(){

				@Override
				public void run() {
					try {
						while(!loginProcessFinished){
							processingLabel.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
							//System.out.println("===========account login processing===============");
							Thread.sleep(1000);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
		    	
		    }).start();
		 
		 new Thread(new Runnable(){

			@Override
			public void run() {
				
				 loginProcessFinished=false;
				    for(AccountVO accountVO:accountList){
				    	try {
							loginResult=accountVO.getMt4ConnectionUtil().coonect(accountVO.getAccountText().getText(),accountVO.getPasswordText().getText());
						} catch (Exception e) {
							e.printStackTrace();
							loginResult=false;
							accountVO.setErrorMessage(e.getMessage());
							System.out.println("************"+accountVO.getAccountText().getText()+" login failed,error message "+e.getMessage());
						}
				    	accountVO.setLoginResult(loginResult);
				    	if(loginResult){
				    		System.out.println("=========="+accountVO.getAccountText().getText()+" login success!");
				    		accountVO.setConnectTime(new Date());
				    	}
			    		loginFinishCount++;
				    }
				    
				    if(loginFinishCount==accountList.size()){
				    	loginProcessFinished=true;
				    	frame.getContentPane().remove(loginPanel);
				    	DataPanel dataPanel=new DataPanel(accountList);
			            frame.getContentPane().add(dataPanel);
			            dataPanel.initTabPanel();
			            frame.revalidate();
				    }
				
			}
			 
		 }).start();
		
	}

}
