package com.lark.middleware.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by houenxun on 16/6/18.
 * spring  context环境工具类,非springBean获取springBean的入口
 */
public class ApplicationContextUtil implements ApplicationContextAware {
    private final static Logger logger = LogManager.getLogger(ApplicationContextUtil.class);


    private static ApplicationContext ctx ;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    public static  <T> T getBean(Class<T> requiredType){
        try {
            return ctx.getBean(requiredType);
        }catch (Exception e){
           logger.error("get bean error requiredType="+requiredType, e);
        }
        return null;
    }

}
