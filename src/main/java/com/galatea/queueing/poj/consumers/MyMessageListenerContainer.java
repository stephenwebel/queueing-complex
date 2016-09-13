package com.galatea.queueing.poj.consumers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.jms.*;
import java.util.Collection;
import java.util.Map;

/**
 * Created by stephenwebel1 on 9/8/16.
 */
public class MyMessageListenerContainer {

    private final Session session;
    private final Queue defaultQueue;
    private final Collection<Listener> listeners = Lists.newArrayList();
    private final Map<String,Listener> clientToMessageListenerMap = Maps.newHashMap();

    public MyMessageListenerContainer(Session session, Queue defaultQueue) {
        this.session = session;
        this.defaultQueue = defaultQueue;
    }

    public void startClientMessageListener(String client) throws JMSException {
        if (!clientToMessageListenerMap.containsKey(client)) {
            System.out.println("Starting listener for client "+client);
            MessageConsumer messageConsumer = session.createConsumer(defaultQueue, "Client = '" + client + "'");
            Listener listener = new Listener(messageConsumer, new MyMessageListener(client));
            listeners.add(listener);
            clientToMessageListenerMap.put(client, listener);
            new Thread(listener).start();
        }else{
            System.out.println("A listener already exists for client "+client);
        }
    }

    public void killAll(){
        listeners.forEach(Listener::kill);
    }

    private class Listener implements Runnable{

        private final MessageConsumer messageConsumer;
        private final MyMessageListener myMessageListener;
        private boolean running;

        private Listener(MessageConsumer messageConsumer, MyMessageListener myMessageListener) {
            this.messageConsumer = messageConsumer;
            this.myMessageListener = myMessageListener;
            this.running = true;
        }

        public void kill(){
            running = false;
        }

        @Override
        public void run() {
//            System.out.println("I'm running");
            while (running){
                Message message = null;
                try {
//                    System.out.println("I'm trying");
                    message = messageConsumer.receive(1000);
//                    System.out.println("message: "+message);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
//                System.out.println("Container received: "+message);
                if(message != null){
                    myMessageListener.onMessage(message);
                }
            }
        }
    }
}
