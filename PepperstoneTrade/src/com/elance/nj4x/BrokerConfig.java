package com.elance.nj4x;

import com.jfx.Broker;

/**
 * Created with IntelliJ IDEA.
 * User: roman
 * Date: 10.06.12
 * Time: 14:21
 */
public class BrokerConfig {
	
	public static final String JFX_ACTIVATION_KEY="xxxx";//input your active key here
	
    public static final Broker MT_4_SERVER = new Broker("MIGBank-Demo");
    public static final Broker PAPER_SERVER = new Broker("Pepperstone-Demo02");
}
