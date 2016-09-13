package com.galatea.queueing.spring;

import com.galatea.queueing.poj.browsers.ClientMessageListenerManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by swebel on 8/25/2016.
 */
public class Main {
//    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
//
//        ThreadPoolTaskExecutor threadPoolTaskExecutor = context.getBean(ThreadPoolTaskExecutor.class);
//        BrowserControllerFactory browserControllerFactory = context.getBean(BrowserControllerFactory.class);
//
//        BrowserRunnable browserRunnable = new BrowserRunnable(browserControllerFactory,threadPoolTaskExecutor);
//        threadPoolTaskExecutor.submit(browserRunnable);
//
//        MessageGenerator messageGenerator = context.getBean(MessageGenerator.class);
//        for(int i = 0; i < 100; i++){
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            messageGenerator.generateAndSendMessage(i);
//        }
//    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        ThreadPoolTaskExecutor threadPoolTaskExecutor = context.getBean(ThreadPoolTaskExecutor.class);
        ClientMessageListenerManager clientMessageListenerManager = context.getBean(ClientMessageListenerManager.class);



        MessageGenerator messageGenerator = context.getBean(MessageGenerator.class);
        for(int i = 0; i < 100; i++){
            messageGenerator.generateAndSendMessage(i);
        }

        threadPoolTaskExecutor.submit(clientMessageListenerManager);
    }
}
