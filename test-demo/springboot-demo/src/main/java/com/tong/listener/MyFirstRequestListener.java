package com.tong.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyFirstRequestListener implements ServletRequestListener {
    private final Logger logger = LoggerFactory.getLogger(MyFirstRequestListener.class);
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        logger.info("call requestDestroyed()");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        logger.info("call requestInitialized()");
    }
}
