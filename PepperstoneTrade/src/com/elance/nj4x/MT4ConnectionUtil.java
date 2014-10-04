package com.elance.nj4x;

import com.jfx.net.JFXServer;
import com.jfx.strategy.Strategy;

import java.io.IOException;


public class MT4ConnectionUtil extends Strategy {

    public void coordinate() {
        // method has been ignored
    }
    
    public boolean coonect(String username,String password,String jfxServerPort){
    	try {
    		System.setProperty("jfx_server_port", jfxServerPort);
    		System.out.println("======= Connecting ========= " + JFXServer.getInstance().getBindPort());
			connect("127.0.0.1", 7788, AccountConfig.PAPER_SERVER, username,password);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    	return true;
    }
    
    public void stopJFXServer(){
    	JFXServer.stop();
    }
    
}
