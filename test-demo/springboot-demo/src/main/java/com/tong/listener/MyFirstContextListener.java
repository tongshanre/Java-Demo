package com.tong.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyFirstContextListener implements ServletContextListener {
    private final Logger logger = LoggerFactory.getLogger(MyFirstContextListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("call contextInitialized()");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("call contextDestroyed()");
    }
}
