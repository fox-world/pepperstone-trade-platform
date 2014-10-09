package com.elance.vo;


import com.elance.nj4x.MT4ConnectionUtil;

public class AccountVO {

	private String account;
	private String password;
	private boolean loginResult;
	private MT4ConnectionUtil mt4ConnectionUtil;
	
	public AccountVO(String account,String password,boolean loginResult,MT4ConnectionUtil mt4ConnectionUtil){
		this.account=account;
		this.password=password;
		this.loginResult=loginResult;
		this.mt4ConnectionUtil=mt4ConnectionUtil;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isLoginResult() {
		return loginResult;
	}
	public void setLoginResult(boolean loginResult) {
		this.loginResult = loginResult;
	}
	public MT4ConnectionUtil getMt4ConnectionUtil() {
		return mt4ConnectionUtil;
	}
	public void setMt4ConnectionUtil(MT4ConnectionUtil mt4ConnectionUtil) {
		this.mt4ConnectionUtil = mt4ConnectionUtil;
	}
	
}
