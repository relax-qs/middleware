/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.lark.middleware.model.dto;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.lark.middleware.model.enums.CommonAttachmentEnum;
import com.lark.middleware.model.inf.ResponseAttachment;

/**
 * 
 * @author liguogang
 * @version $Id: Result.java, v 0.1 2016年3月19日 下午11:15:23 liguogang Exp $
 */
public class Response<T> implements Serializable {

    /**  */
    private static final long   serialVersionUID = 1L;
    // 串联一次上线文的唯一ID
    private String              traceId;
    // 返回的数据
    private T                   data;
    // transform data,透传数据
    private Map<String, Object> transData;
    // 是否阻断主流程继续往下执行
    private boolean             isInterrupt;
    // 返回结果是否成功
    private boolean             isSuccess;
    // 返回结果的状态码
    private String              code;
    // 返回结果的描述
    private String              desc;


    /**
     * 默认结果是成功
     */
    public Response(){
        this.attachment(CommonAttachmentEnum.SUCCESS.getAttachment());
    }

    /**
     * 带结果的构造函数 默认成功
     */
    public Response(T data){
        this.data = data;
        this.attachment(CommonAttachmentEnum.SUCCESS.getAttachment());
    }

    public <S> Response(Request<S> request){
        this.traceId = request.getTraceId();
        this.transData = request.getTransData();
        this.attachment(CommonAttachmentEnum.SUCCESS.getAttachment());

    }

    public <S> Response(Request<S> request, T data){
        this.traceId = request.getTraceId();
        this.transData = request.getTransData();
        this.data = data;
        this.attachment(CommonAttachmentEnum.SUCCESS.getAttachment());

    }

    /***
     * 返回结果的附件
     * 
     * @param attachment
     */
    public final void attachment(final ResponseAttachment attachment) {
        if (null != attachment) {
            this.isInterrupt = attachment.isInterrupt();
            this.isSuccess = attachment.isSuccess();
            this.code = attachment.getCode();
            this.desc = attachment.getDesc();
        }
    }
    
    /***
     * 返回结果的附件
     * 
     * @param attachment
     */
    public final void attachment(final ResponseAttachment attachment,String dynamicDesc) {
        if (null != attachment) {
            this.isInterrupt = attachment.isInterrupt();
            this.isSuccess = attachment.isSuccess();
            this.code = attachment.getCode();
            this.desc = attachment.getDesc();
            if (null != dynamicDesc) {
				this.desc = this.desc==null?dynamicDesc:(this.desc+",["+dynamicDesc+"]");
			}
        }
    }


    /***
     * 从另外一个结果中拷贝附件
     * 
     * @param sourceResponse
     */
    public final void copyAttachment(final Response<?> sourceResponse) {
        if (null != sourceResponse) {
            this.isInterrupt = sourceResponse.isInterrupt;
            this.isSuccess = sourceResponse.isSuccess;
            this.code = sourceResponse.code;
            this.desc = sourceResponse.desc;
        }
    }
    
    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getTransData() {
        return transData;
    }

    public void setTransData(Map<String, Object> transData) {
        this.transData = transData;
    }

    public boolean isInterrupt() {
        return isInterrupt;
    }

    public void setInterrupt(boolean isInterrupt) {
        this.isInterrupt = isInterrupt;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
