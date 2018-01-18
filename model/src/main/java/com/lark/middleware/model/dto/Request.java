/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.lark.middleware.model.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * @author liguogang
 * @version $Id: Request.java, v 0.1 2016年3月19日 下午11:35:53 liguogang Exp $
 */
public class Request<T> implements Serializable {
    /**  */
    private static final long   serialVersionUID = 1L;
    // 一次请求串通上下文链路的唯一ID
    private String              traceId;
    // 请求的参数
    private T                   param;
    // 请求参数中的扩展参数
    private Map<String, Object> extendParams;
    // 请求参数中的透传数据,要求回传到response
    private Map<String, Object> transData;

    // 默认构造函数
    public Request() {

    }

    // 带参数的构造函数
    public Request(T param) {
        this.param = param;
        traceId = UUID.randomUUID().toString();
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }

    public Map<String, Object> getExtendParams() {
        return extendParams;
    }

    public void setExtendParams(Map<String, Object> extendParams) {
        this.extendParams = extendParams;
    }

    public Map<String, Object> getTransData() {
        return transData;
    }

    public void setTransData(Map<String, Object> transData) {
        this.transData = transData;
    }

    public void addExtendParam(String key, Object obj) {
        if (null == extendParams) {
            this.extendParams = new HashMap<String, Object>();
        }

        this.extendParams.put(key, obj);
    }

    public Object fetchParam(String key) {
        if (null == this.extendParams) {
            return null;
        }
        return this.extendParams.get(key);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
