package com.webel.queueing.spring;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.SessionCallback;

import javax.jms.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by swebel on 8/25/2016.
 */
public class BrowserController implements Callable<Boolean> {

    private final JmsTemplate jmsTemplate;
    private final Map<String, MessageConsumer> messageConsumerMap;

    protected BrowserController(JmsTemplate jmsTemplate, Map<String, MessageConsumer> messageConsumerMap) {
        this.jmsTemplate = jmsTemplate;
        this.messageConsumerMap = messageConsumerMap;
    }

//    @Override
    public void run() {
    }

    @Override
    public Boolean call() throws Exception {
        System.out.println("Running Browser Controller");
        System.out.println("New Browser");
        Boolean result = jmsTemplate.browse((session, queueBrowser) -> {
            System.out.println("session: " + session);
            System.out.println("queueBrowser: " + queueBrowser);
            Enumeration<Message> messageEnumeration = queueBrowser.getEnumeration();
            System.out.println("queueBrowser.getQueue(): " + queueBrowser.getQueue());
            System.out.println("Enumberation: " + messageEnumeration);
            messageEnumeration.hasMoreElements(); //true
            messageEnumeration.nextElement(); // null
            Message message = messageEnumeration.nextElement();
            if (message == null) {
                System.out.println("Null");
                return false;
            }
            String clientName = message.getStringProperty("Client");

            if (clientName != null) {
                System.out.println("Browsing Message For Client " + clientName);
                if (!messageConsumerMap.containsKey(clientName)) {
                    MessageConsumer messageConsumer = session.createConsumer(jmsTemplate.getDefaultDestination(), "Client = '" + clientName + "'");
                    messageConsumer.setMessageListener(new ClientMessageListener(clientName));
                    messageConsumerMap.put(clientName, messageConsumer);
                }
                return true;
            }

            return false;
        });
        return result;
    }

    private class MySessionCallback implements SessionCallback<Boolean> {

        @Override
        public Boolean doInJms(Session session) throws JMSException {
            System.out.println("Starting new QueueBrowser");
            System.out.println("Session type: " + session.getClass().getName());

            QueueBrowser browser = session.createBrowser((Queue) jmsTemplate.getDefaultDestination());
            System.out.println("Checking the next enumeration of the queue");
            Enumeration<Message> messages = browser.getEnumeration();
            Message browsedMessage = messages.nextElement();
            while (browsedMessage != null) {
                System.out.println("More elements? " + messages.hasMoreElements());
                String clientName = null;
                try {
                    clientName = browsedMessage.getStringProperty("Client");
                } catch (NullPointerException e) {
                }
                if (clientName != null) {
                    System.out.println("Browsing Message For Client " + clientName);
                    if (!messageConsumerMap.containsKey(clientName)) {
                        MessageConsumer messageConsumer = session.createConsumer(jmsTemplate.getDefaultDestination(), "Client = '" + clientName + "'");
                        messageConsumer.setMessageListener(new ClientMessageListener(clientName));
                        messageConsumerMap.put(clientName, messageConsumer);
                    }
                }
                //Increment the browserMessage pointer
                browsedMessage = messages.nextElement();
            }
            System.out.println("Done checking enumeration of the queue");
            browser.close();
            return null;
        }
    }
}
