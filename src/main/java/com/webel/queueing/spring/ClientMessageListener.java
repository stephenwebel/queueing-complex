package com.webel.queueing.spring;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by swebel on 8/25/2016.
 */
public class ClientMessageListener implements MessageListener {

    private final String client;

    public ClientMessageListener(String client){
        this.client = client;
        System.out.println("Listening for client: "+client);
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("Responsible for Client "+this.client+" Received message for "+" Client: "+message.getStringProperty("Client")+" Text: "+((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
