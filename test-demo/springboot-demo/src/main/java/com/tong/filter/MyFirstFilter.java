package com.tong.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class MyFirstFilter implements Filter {
    private  final Logger logger = LoggerFactory.getLogger(MyFirstFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("call init()");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("call doFilter..");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.info("call destroy()");
    }
}
