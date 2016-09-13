package com.webel.queueing.poj.producers;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

/**
 * Created by stephenwebel1 on 9/8/16.
 */
public class Messenger {

    private final TextMessageFactory textMessageFactory;
    private final MessageProducer messageProducer;
    int numClients = 3;
    String[] clients = new String[numClients];

    public Messenger(TextMessageFactory textMessageFactory, MessageProducer messageProducer) {
        this.textMessageFactory = textMessageFactory;
        this.messageProducer = messageProducer;
        for(int i = numClients-1; i >= 0; i--){
            clients[i] = String.valueOf(i);
        }
    }

    public boolean send(int i) throws JMSException{
        System.out.println("Sending "+i+" messages");
        for(; i>0; i--){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TextMessage textMessage = textMessageFactory.genMessage();
            textMessage.setText("Haaayyyy, I'm Client "+clients[i%clients.length]);
            textMessage.setStringProperty("Client",clients[i%clients.length]);
            messageProducer.send(textMessage);
        }
        return true;
    }
}
