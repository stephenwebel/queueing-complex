package com.webel.queueing.spring;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.MessageConsumer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by swebel on 9/7/2016.
 */
@Component
public class BrowserControllerFactory {

    public final JmsTemplate jmsTemplate;

    final ConcurrentHashMap<String, MessageConsumer> messageConsumerMap = new ConcurrentHashMap<>();

    public BrowserControllerFactory(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public BrowserController generateBrowserController(){
        return new BrowserController(jmsTemplate,messageConsumerMap);
    }
}
