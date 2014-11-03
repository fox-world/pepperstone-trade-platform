package com.elance.vo;


import java.util.Date;

import javax.swing.JTextField;

import com.elance.nj4x.MT4ConnectionUtil;

public class AccountVO {

	private JTextField accountText;
	private JTextField passwordText;
	private boolean loginResult;
	private String errorMessage;
	private Date connectTime;
	private double totalLotsForNextTrade;
	
	private MT4ConnectionUtil mt4ConnectionUtil;
	
	public AccountVO(){
	}
	
	public AccountVO(JTextField accountText,JTextField passwordText){
		this.accountText=accountText;
		this.passwordText=passwordText;
	}
	
	public AccountVO(JTextField accountText,JTextField passwordText,MT4ConnectionUtil mt4ConnectionUtil){
		this(accountText,passwordText);
		this.mt4ConnectionUtil=mt4ConnectionUtil;
	}
	
	public AccountVO(JTextField accountText,JTextField passwordText,boolean loginResult,MT4ConnectionUtil mt4ConnectionUtil){
		this(accountText,passwordText,mt4ConnectionUtil);
		this.loginResult=loginResult;
	}

	public JTextField getAccountText() {
		return accountText;
	}

	public void setAccountText(JTextField accountText) {
		this.accountText = accountText;
	}

	public JTextField getPasswordText() {
		return passwordText;
	}

	public void setPasswordText(JTextField passwordText) {
		this.passwordText = passwordText;
	}

	public boolean isLoginResult() {
		return loginResult;
	}

	public void setLoginResult(boolean loginResult) {
		this.loginResult = loginResult;
	}
    
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
    
	public Date getConnectTime() {
		return connectTime;
	}

	public void setConnectTime(Date connectTime) {
		this.connectTime = connectTime;
	}

	public MT4ConnectionUtil getMt4ConnectionUtil() {
		return mt4ConnectionUtil;
	}

	public void setMt4ConnectionUtil(MT4ConnectionUtil mt4ConnectionUtil) {
		this.mt4ConnectionUtil = mt4ConnectionUtil;
	}

	public double getTotalLotsForNextTrade() {
		return totalLotsForNextTrade;
	}

	public void setTotalLotsForNextTrade(double totalLotsForNextTrade) {
		this.totalLotsForNextTrade = totalLotsForNextTrade;
	}
}
