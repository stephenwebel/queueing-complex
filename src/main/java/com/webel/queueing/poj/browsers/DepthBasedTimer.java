package com.webel.queueing.poj.browsers;

import org.springframework.util.StopWatch;

import javax.jms.*;
import java.util.Enumeration;

/**
 * Created by stephenwebel1 on 9/8/16.
 */
public class DepthBasedTimer implements Runnable{
    private final Session session;
    private final Queue defaultQueue;

    StopWatch stopWatch = new StopWatch();
    boolean isEmpty = true;

    public DepthBasedTimer(Session session, Queue defaultQueue) {
        this.session = session;
        this.defaultQueue = defaultQueue;
    }
    @Override
    public void run() {
        stopWatch.start("Empty Queue");
        try {
            int maxCount = 0;
            while (true) {
                QueueBrowser queueBrowser = session.createBrowser(defaultQueue);
                Enumeration<Message> messageEnumeration = queueBrowser.getEnumeration();
                int count = 0;
                while (messageEnumeration.hasMoreElements()) {
                    TextMessage textMessage = (TextMessage) messageEnumeration.nextElement();
                    count++;
                }
                if (maxCount<count){
                    maxCount=count;
                }

                if(count>0 && isEmpty){
                    stopWatch.stop();
                    System.out.println(stopWatch.prettyPrint());
                    isEmpty = false;
                    stopWatch.start("Fill and Drain");
                }else if (count==0 && !isEmpty){
                    stopWatch.stop();
                    System.out.println(stopWatch.prettyPrint());
                    System.out.println("MaxCount: "+maxCount);
                    isEmpty = true;
                    stopWatch.start("Empty Queue");
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
