package com.lark.middleware.common.service.listener.impl;

import com.lark.middleware.common.service.listener.ConfigListenerService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/***
 * 配置监听实现类，基于redis的实现
 * 
 * @author liguogang
 * @version $Id: ConfigListenerServiceImpl.java, v 0.1 2016年5月30日 下午6:11:45 liguogang Exp $
 */
@Service
public class ConfigRedisListenerServiceImpl implements ConfigListenerService, InitializingBean, ApplicationContextAware {
    private ApplicationContext applicationContext;

    public void listener() {

    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {

    }
}
