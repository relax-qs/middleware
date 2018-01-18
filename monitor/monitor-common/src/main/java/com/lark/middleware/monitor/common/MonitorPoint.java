package com.lark.middleware.monitor.common;

/**
 * Created by houenxun on 2017/3/24.
 */
public class MonitorPoint {
    protected String name;
    protected String code;
    protected MonitorPoint parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MonitorPoint getParent() {
        return parent;
    }

    public void setParent(MonitorPoint parent) {
        this.parent = parent;
    }
}
