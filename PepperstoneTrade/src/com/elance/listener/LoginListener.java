package com.elance.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.elance.vo.AccountVO;

public class LoginListener implements ActionListener {
	
	private JFrame frame;
	private JPanel loginPanel;
	private JPanel dataPanel;
	private List<AccountVO> accountList;
	
	private JLabel processingLabel;
	private boolean loginProcessFinished;
	private int loginSuccessCount;
	private boolean loginResult;
	
    public LoginListener(){
    }
    
    public LoginListener(JFrame frame,JPanel loginPanel,JPanel dataPanel,JLabel processingLabel,List<AccountVO> accountList){
    	this.frame=frame;
    	this.loginPanel=loginPanel;
    	this.dataPanel=dataPanel;
    	this.accountList=accountList;
    	this.processingLabel=processingLabel;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		loginProcessFinished=false;
		loginSuccessCount=0;
		loginResult=false;
		
	    processingLabel.setVisible(true);
		
		 new Thread(new Runnable(){

				@Override
				public void run() {
					try {
						while(!loginProcessFinished){
							processingLabel.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
							System.out.println("===========account login processing===============");
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
				    	loginResult=accountVO.getMt4ConnectionUtil().coonect(accountVO.getAccountText().getText(),accountVO.getPasswordText().getText());
				    	accountVO.setLoginResult(loginResult);
				    	if(loginResult){
				    		System.out.println("=========="+accountVO.getAccountText().getText()+" login success!");
				    		loginSuccessCount++;
				    	}
				    }
				    
				    if(loginSuccessCount==accountList.size()){
				    	loginProcessFinished=true;
				    	frame.setTitle("Login Success");
				    	frame.getContentPane().remove(loginPanel);
			            frame.getContentPane().add(dataPanel);
			            frame.revalidate();
				    }
				
			}
			 
		 }).start();
		
	}

}
