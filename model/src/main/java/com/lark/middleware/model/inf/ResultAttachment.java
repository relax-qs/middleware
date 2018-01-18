/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.lark.middleware.model.inf;

/**
 * 
 * @author liguogang
 * @version $Id: ResultAttachment.java, v 0.1 2016年3月19日 下午11:50:41 liguogang Exp $
 */
public class ResultAttachment implements ResponseAttachment {
    // 是否阻断主流程继续往下执行
    private boolean isInterrupt;
    // 返回结果是否成功
    private boolean isSuccess;
    // 返回结果的状态码
    private String  code;
    // 返回结果的描述
    private String  desc;

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

}
