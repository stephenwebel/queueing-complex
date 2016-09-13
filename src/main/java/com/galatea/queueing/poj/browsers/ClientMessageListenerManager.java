package com.galatea.queueing.poj.browsers;


import com.galatea.queueing.poj.consumers.MyMessageListenerContainer;

import javax.jms.*;
import java.util.Enumeration;

/**
 * Created by swebel on 9/7/2016.
 */
public class ClientMessageListenerManager implements Runnable {

    private final Session session;
    private final Queue defaultQueue;
    private final MyMessageListenerContainer myMessageListenerContainer;


    public ClientMessageListenerManager(Session session, Queue defaultQueue,MyMessageListenerContainer myMessageListenerContainer) {
        this.session = session;
        this.defaultQueue = defaultQueue;
        this.myMessageListenerContainer = myMessageListenerContainer;
    }


    @Override
    public void run() {
        try {
            while (true) {
                try {
                    Thread.sleep(100);
                }catch (Throwable e){}
                QueueBrowser queueBrowser = session.createBrowser(defaultQueue);
                Enumeration<Message> messageEnumeration = queueBrowser.getEnumeration();
                while (messageEnumeration.hasMoreElements()) {
                    TextMessage textMessage = (TextMessage) messageEnumeration.nextElement();
                    myMessageListenerContainer.startClientMessageListener(textMessage.getStringProperty("Client"));
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
