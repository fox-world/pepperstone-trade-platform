package com.elance.thread;

import java.util.concurrent.CountDownLatch;

import com.elance.vo.AccountVO;

public class AccountLoginThread implements Runnable {
	
	public AccountVO accountVO;
	public CountDownLatch countDownLatch;
	
	public AccountLoginThread(AccountVO accountVO,CountDownLatch countDownLatch){
		this.accountVO=accountVO;
		this.countDownLatch=countDownLatch;
	}
	
	@Override
	public void run() {
		boolean loginResult=accountVO.getMt4ConnectionUtil().coonect(accountVO.getAccount(), accountVO.getPassword(), accountVO.getServerPort());
        accountVO.setLoginResult(loginResult);
        countDownLatch.countDown();
        if(loginResult){
            System.out.println(accountVO.getMt4ConnectionUtil().accountNumber());	
        }
	}

}
