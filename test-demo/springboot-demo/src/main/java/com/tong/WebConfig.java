package com.tong;

import com.tong.interceptor.MyFirstInterceptor;
import com.tong.listener.MyFirstSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private MyFirstInterceptor interceptor;

    /**
     * 设置拦截器
     */
    /*
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/*");
    }*/

    /**
     * 注册过滤器
     * @return
     */
  /*  @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new MyFirstFilter());
        bean.addUrlPatterns("/*");
        return bean;
    }*/

    /**
     * 注册监听器
     * @return
     */
   /* @Bean
    public ServletListenerRegistrationBean listenerRegistrationBean(){
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
        bean.setListener(new MyFirstContextListener());
        return bean;
    }*/
/*
    @Bean
    public ServletListenerRegistrationBean sessionListenerRegistrationBean(){
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
        bean.setListener(new MyFirstSessionListener());
        return bean;
    }*/

   /* @Bean
    public ServletListenerRegistrationBean requestlistenerRegistrationBean(){
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
        bean.setListener(new MyFirstRequestListener());
        return bean;
    }*/
}
