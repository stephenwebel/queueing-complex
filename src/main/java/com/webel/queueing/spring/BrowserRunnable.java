package com.webel.queueing.spring;


import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by swebel on 9/7/2016.
 */
public class BrowserRunnable implements Runnable {

    private final BrowserControllerFactory browserControllerFactory;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public BrowserRunnable(BrowserControllerFactory browserControllerFactory, ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.browserControllerFactory = browserControllerFactory;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Override
    public void run() {
        System.out.println("BrowserRunnable Started");
        while (true) {
            System.out.println("Starting New Browser Controller");
            Future<Boolean> future = this.threadPoolTaskExecutor.submit(this.browserControllerFactory.generateBrowserController());
            try {
                Boolean result = future.get();
                if (result) {
                    System.out.println("Started a new listener");
                }else {
                    System.out.println("Did not start new listener");
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
