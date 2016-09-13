package com.galatea.queueing.spring;

/**
 * Created by swebel on 8/25/2016.
 */

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

public class QueueSender {
    private final JmsTemplate jmsTemplate;

    public QueueSender(final JmsTemplate jmsTemplateCached) {
        this.jmsTemplate = jmsTemplateCached;
    }

    public void send(final String message, final int i) {
        jmsTemplate.send(new MyMessageCreator(message,i));
    }

    private class MyMessageCreator implements MessageCreator{

        private final String message;
        private final int i;

        public MyMessageCreator(String message, int i){
            this.message = message;
            this.i = i;
        }

        public Message createMessage(Session session) throws JMSException {
            TextMessage textMessage = session.createTextMessage(message+i);
            textMessage.setJMSCorrelationID("" + i);
            textMessage.setStringProperty("Client",message);
            return textMessage;
        }
    }
}