package com.elance.nj4x;

import com.jfx.Broker;

/**
 * Created with IntelliJ IDEA.
 * User: roman
 * Date: 10.06.12
 * Time: 14:21
 */
public class BrokerConfig {

   public static final String JFX_ACTIVATION_KEY = "974274799"; // ke for me
   //public static final String JFX_ACTIVATION_KEY = "55395048 "; // key for phil

    //public static final Broker MT_4_SERVER = new Broker("MIGBank-Demo");
    public static final Broker PAPER_SERVER = new Broker(System.getProperty("default_broker", "Pepperstone-Demo02").trim());
    //public static final Broker PAPER_SERVER = new Broker(System.getProperty("default_broker", "Pepperstone-Edge04").trim());

    static {
        //System.setProperty("jfx_server_port","7777");
        //noinspection ConstantConditions
        if (JFX_ACTIVATION_KEY != null) {
            System.setProperty("jfx_activation_key", JFX_ACTIVATION_KEY);
        }
    }
}
