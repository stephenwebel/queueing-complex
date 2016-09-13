package com.webel.queueing.spring;

/**
 * Created by swebel on 8/25/2016.
 */
public class MessageGenerator {

    private final QueueSender queueSender;

    final String[] clients = {"A","B","C"};

    public MessageGenerator(QueueSender queueSender) {
        this.queueSender = queueSender;
    }

    public void generateAndSendMessage(int i){
        String client = clients[i%3];
//        System.out.println("Created Message with Client: "+client+" and i: "+i);
        this.queueSender.send(client,i);
    }
}
