package com.galatea.queueing.poj.consumers;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by stephenwebel1 on 9/8/16.
 */
public class MyMessageListener implements MessageListener {

    String client;

    public MyMessageListener(String client){
        System.out.println("Hello! I will be listening for only Client "+client);
        this.client = client;
    }

    @Override
    public void onMessage(Message message) {
//        try {
//            if (message.getStringProperty("Client").equals(client) ){
//
//            }
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//        System.out.println("MyMessageListener for client "+client+" onMessage: "+message);
    }
}
