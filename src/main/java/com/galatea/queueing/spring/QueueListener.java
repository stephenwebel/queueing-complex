package com.galatea.queueing.spring;

/**
 * Created by swebel on 8/25/2016.
 */
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class QueueListener implements MessageListener {
    public void onMessage(final Message message) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (message instanceof TextMessage) {
            final TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println(textMessage.getText());
            } catch (final JMSException e) {
                e.printStackTrace();
            }
        }
    }
}