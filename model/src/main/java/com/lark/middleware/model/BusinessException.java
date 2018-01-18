package com.lark.middleware.model;


import com.lark.middleware.model.enums.CommonAttachmentEnum;
import com.lark.middleware.model.inf.ResponseAttachment;
import com.lark.middleware.model.inf.ResultAttachment;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by houenxun on 16/7/10.
 * 业务异常
 */
public class BusinessException extends RuntimeException {
    /**
     * 运行环境的参数信息
     */
    private Map<String, Object> paramMap = new HashMap<>();
    /**
     * 错误描述附件
     */
    final private ResponseAttachment attachment;
    /**
     * 错误描述,如果desc不空使用desc否则使用attachment.getDesc()
     */
    private String desc;

    /**
     * @param attachment
     */
    public BusinessException(ResponseAttachment attachment) {
        this.attachment = attachment;
    }

    /**
     * 构造错误信息,并对错误详情进行定制
     *
     * @param attachment
     * @param desc
     */
    public BusinessException(ResponseAttachment attachment, String desc) {
        this.attachment = attachment;
        this.desc = desc;
    }

    /**
     * 根据原始异常构造错误异常信息
     *
     * @param throwable
     */
    public BusinessException(Throwable throwable) {
        super(throwable);
        if (throwable instanceof BusinessException) {
            this.attachment = ((BusinessException) throwable).attachment;
            this.desc = ((BusinessException) throwable).desc;
            this.paramMap = ((BusinessException) throwable).paramMap;
        } else {
            this.attachment = CommonAttachmentEnum.SYSETM_ERROR.getAttachment();
        }
    }

    /**
     * @param throwable
     * @param attachment
     * @param desc
     */
    public BusinessException(Throwable throwable, ResponseAttachment attachment, String desc) {
        super(throwable);
        this.attachment = attachment;
        this.desc = desc;
    }

    public BusinessException(String code, String desc) {
        super();
        attachment = new ResultAttachment();
        ((ResultAttachment) attachment).setCode(code);
        ((ResultAttachment) attachment).setDesc(desc);
        ((ResultAttachment) attachment).setInterrupt(true);
        ((ResultAttachment) attachment).setSuccess(false);
    }


    /**
     * 增加抛出异常的异常参数
     *
     * @param key
     * @param value
     * @return
     */
    public BusinessException addParam(String key, Object value) {
        paramMap.put(key, value);
        return this;
    }

    public ResponseAttachment getAttachment() {
        return attachment;
    }

    public String getDesc() {
        if (StringUtils.isNotBlank(desc)) {
            return desc;
        } else {
            if (null != this.attachment) {
                return attachment.getDesc();
            }
            return null;
        }
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        if (null != attachment) {
            if (StringUtils.isNotBlank(attachment.getCode())) {
                builder.append(attachment.getCode()).append(":");
            }
        }
        if (StringUtils.isNotBlank(getDesc())) {
            builder.append(getDesc()).append(":");
        }
        if (null != paramMap && paramMap.size() > 0) {
            builder.append(paramMap);
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return this.getClass().getName()
                + "\n" + getMessage();
    }
}
