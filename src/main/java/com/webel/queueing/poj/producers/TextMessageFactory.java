package com.webel.queueing.poj.producers;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by stephenwebel1 on 9/8/16.
 */
public class TextMessageFactory {

    private final Session session;

    public TextMessageFactory(Session session) {
        this.session = session;
    }

    public TextMessage genMessage() throws JMSException {
        return session.createTextMessage();
    }
}
