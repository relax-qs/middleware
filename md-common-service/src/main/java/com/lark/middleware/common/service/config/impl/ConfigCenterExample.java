package com.lark.middleware.common.service.config.impl;

import com.lark.middleware.common.service.config.ConfigCenterAware;
import org.springframework.stereotype.Service;

@Service
public class ConfigCenterExample implements ConfigCenterAware {
    private boolean test;

    private String  name;

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
