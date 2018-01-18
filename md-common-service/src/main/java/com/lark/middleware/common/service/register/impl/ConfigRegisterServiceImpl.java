package com.lark.middleware.common.service.register.impl;

import com.lark.middleware.common.service.config.ConfigCenterAware;
import com.lark.middleware.common.service.notify.ConfigPushService;
import com.lark.middleware.common.service.register.ConfigRegisterService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/***
 * config的注册器，在系统启动时，完成注册
 * 
 * @author liguogang
 * @version $Id: ConfigRegisterServiceImpl.java, v 0.1 2016年5月29日 下午11:01:10 liguogang Exp $
 */
@Service
public class ConfigRegisterServiceImpl implements ConfigRegisterService, InitializingBean, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Autowired
    private ConfigPushService configPushService;

    public void register() {

    }

    public void afterPropertiesSet() throws Exception {
        Map<String, ConfigCenterAware> configCenterMap = applicationContext.getBeansOfType(ConfigCenterAware.class);
        if (CollectionUtils.isEmpty(configCenterMap)) {
            return;
        }
        for (ConfigCenterAware configCenterAware : configCenterMap.values()) {
            // 加载 configCenter的只
            //configPushService.load(configCenterAware);
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
