package com.elance.nj4x;
/*
 * Metatrader Java (JFX) / .Net (NJ4X) library
 * Copyright (c) 2008-2014 by Gerasimenko Roman.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistribution of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistribution in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The names "JFX" or "NJ4X" must not be used to endorse or
 * promote products derived from this software without prior
 * written permission. For written permission, please contact
 * roman.gerasimenko@nj4x.com
 *
 * 4. Products derived from this software may not be called "JFX" or
 * "NJ4X", nor may "JFX" or "NJ4X" appear in their name,
 * without prior written permission of Gerasimenko Roman.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE JFX CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

import com.jfx.net.JFXServer;
import com.jfx.strategy.Strategy;

import java.io.IOException;

/**
 * JFX Strategy used as a simple MT4 connection.
 * This demo is to show that coordinate() method can be ignored.
 */
public class MT4ConnectionExample extends Strategy {

    public void connect() throws IOException {
        //connect("127.0.0.1", 7788, DemoAccount.MT_4_SERVER, DemoAccount.MT_4_USER, DemoAccount.MT_4_PASSWORD);
        connect("127.0.0.1", 7788, DemoAccount.PAPER_SERVER, "68052", "h7siwcva");
    }

    public void coordinate() {
        // method has been ignored
    }

    public static void main(String[] args) throws IOException{
//        MT4ConnectionExample mt4c = new MT4ConnectionExample();
//        System.out.println("======= Connecting ========= " + JFXServer.getInstance().getBindPort());
//        mt4c.connect();
//        System.out.println("======= Connected ========= ");
//        System.out.println("Account info: balance=" + mt4c.accountBalance() + ", equity=" + mt4c.accountEquity());
//        System.out.println("Account info: symbols=" + mt4c.getSymbols());
//        System.out.println("======= Disconnecting ========= ");
//        mt4c.close(true);
//        System.out.println("======= Disconnected ========= ");
//        JFXServer.stop();
    }
    
    public boolean coonect(String username,String password,String jfxServerPort){
    	boolean result=true;
    	try {
    		System.setProperty("jfx_server_port", jfxServerPort);
    		System.out.println("======= Connecting ========= " + JFXServer.getInstance().getBindPort());
			connect("127.0.0.1", 7788, DemoAccount.PAPER_SERVER, username,password);
		} catch (IOException e) {
			e.printStackTrace();
			result=false;
		}
    	return result;
    }
    
    public void stopJFXServer(){
    	JFXServer.stop();
    }
    
}
