package com.tong.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class MyFirstSessionListener implements HttpSessionListener {
    private final Logger logger = LoggerFactory.getLogger(MyFirstSessionListener.class);
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("call sessionCreated()");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("call sessionDestroyed()");
    }
}
