package com.elance.nj4x;

import com.jfx.net.JFXServer;
import com.jfx.strategy.Strategy;

public class MT4ConnectionUtil extends Strategy {

    public void coordinate() {
        // method has been ignored
    }
    
    public boolean coonect(String username,String password) throws Exception{
    	System.setProperty("jfx_server_port","7777");
    	System.out.println("======= Connecting ========= " + JFXServer.getInstance().getBindPort());
	    connect("127.0.0.1", 7788, AccountConfig.PAPER_SERVER, username,password);
    	return true;
    }
    
    public void stopJFXServer(){
    	JFXServer.stop();
    }
    
}
