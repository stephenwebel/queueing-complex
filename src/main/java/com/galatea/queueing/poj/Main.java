package com.galatea.queueing.poj;

import com.galatea.queueing.poj.browsers.ClientMessageListenerManager;
import com.galatea.queueing.poj.browsers.DepthBasedTimer;
import com.galatea.queueing.poj.consumers.MyMessageListenerContainer;
import com.galatea.queueing.poj.producers.Messenger;
import com.galatea.queueing.poj.producers.TextMessageFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by stephenwebel1 on 9/8/16.
 */
public class Main {
    public static void main(String[] args) throws URISyntaxException, JMSException, InterruptedException {
        System.out.println("Go!");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("admin","admin",new URI("tcp://localhost:61616"));

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);


        Queue defaultQueue = session.createQueue("request");

        TextMessageFactory textMessageFactory = new TextMessageFactory(session);
        MessageProducer messageProducer = session.createProducer(defaultQueue);

        ClientMessageListenerManager clientMessageListenerManager = new ClientMessageListenerManager(session,defaultQueue,new MyMessageListenerContainer(session,defaultQueue));
        new Thread(clientMessageListenerManager).start();

        DepthBasedTimer depthBasedTimer = new DepthBasedTimer(session,defaultQueue);
        new Thread(depthBasedTimer).start();

        Messenger messenger = new Messenger(textMessageFactory,messageProducer);

        for (int trial = 1; trial > 0; trial--) {
            messenger.send(10);

            Thread.sleep(5000);
        }
    }

}
