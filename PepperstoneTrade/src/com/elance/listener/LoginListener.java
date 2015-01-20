package com.elance.listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.elance.component.DataPanel;
import com.elance.component.LoginPanel;
import com.elance.vo.AccountConfig;
import com.elance.vo.AccountVO;

public class LoginListener implements ActionListener {

    private JFrame frame;
    private LoginPanel loginPanel;
    private List<AccountVO> accountList;
    private AccountConfig accountConfig;

    private JLabel processingLabel;
    private boolean loginProcessFinished;
    private int loginFinishCount;
    private boolean loginResult;

    public LoginListener() {
    }

    public LoginListener(JFrame frame, LoginPanel loginPanel, JLabel processingLabel, List<AccountVO> accountList, AccountConfig accountConfig) {
        this.frame = frame;
        this.loginPanel = loginPanel;
        this.accountList = accountList;
        this.processingLabel = processingLabel;
        this.accountConfig = accountConfig;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        loginProcessFinished = false;
        loginFinishCount = 0;
        loginResult = false;

        processingLabel.setVisible(true);
        loginPanel.changeComponentsEnableStatus(false);

        ExecutorService threadPool = Executors.newCachedThreadPool();

        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                int num = 0;
                StringBuffer sb = new StringBuffer();
                try {
                    while (!loginProcessFinished) {
                        sb.setLength(0);
                        sb.append("Login processing");
                        num = ++num % 8;
                        for (int i = 0; i < num; i++) {
                            sb.append("••");
                        }
                        processingLabel.setText(sb.toString());
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadPool.submit(new Runnable() {
            @Override
            public void run() {

                loginProcessFinished = false;
                String account = null;
                AccountVO accountVO=null;
                int availableAccountCount=Integer.parseInt(accountConfig.getServerNumberSpinner().getValue().toString());
                for (int i=0;i<availableAccountCount;i++) {
                	accountVO=accountList.get(i);
                    account = accountVO.getAccountText().getText();
                    try {
                        loginResult = account != null && account.length() > 0 && accountVO.getMt4ConnectionUtil().connect(account, accountVO.getPasswordText().getText(),accountConfig.getServerNameText().getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                        loginResult = false;
                        accountVO.setErrorMessage("<html>Account " + account + " login failed,reason:<br/>" + e.getMessage() + "</html>");
                        System.out.println("************" + account + " login failed,error message " + e.getMessage());
                    }
                    accountVO.setLoginSuccess(loginResult);
                    if (loginResult) {
                        System.out.println("==========" + account + " login success!");
                        accountVO.getAccountText().setForeground(Color.BLUE);
                        accountVO.setConnectTime(new Date());
                    }else{
                    	accountVO.getAccountText().setForeground(Color.RED);
                    }
                    loginFinishCount++;
                }

                if (loginFinishCount == availableAccountCount) {
                    loginProcessFinished = true;
                    frame.getContentPane().remove(loginPanel);
                    DataPanel dataPanel = new DataPanel(frame,accountList, accountConfig);
                    frame.getContentPane().add(dataPanel);
                    dataPanel.initTabPanel();
                    frame.revalidate();
                }
            }
        });

        threadPool.shutdown();
    }

}
