package com.lark.middleware.task.schedule.api.result;

import com.lark.middleware.model.inf.ResponseAttachment;

/**
 * Created by houenxun on 16/8/24.
 * 错误信息返回
 */
public class ErrorHandleResult implements IHandleResult{
    protected String errorCode;
    protected String errorDesc;

    public ErrorHandleResult(ResponseAttachment attachment){
        this(attachment.getCode(), attachment.getDesc());
    }


    public ErrorHandleResult(String code, String desc){
        this.errorCode = code;
        this.errorDesc = desc;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
