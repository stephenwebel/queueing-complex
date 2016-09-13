package com.webel.queueing.spring;

/**
 * Created by swebel on 8/25/2016.
 */

import org.springframework.stereotype.Component;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

@Component
public class JmsExceptionListener implements ExceptionListener {
    public void onException(final JMSException e) {
        e.printStackTrace();
    }
}
