/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.lark.middleware.model.enums;

import com.lark.middleware.model.inf.ResponseAttachment;
import com.lark.middleware.model.inf.ResultAttachment;

/**
 * 
 * @author liguogang
 * @version $Id: CommonAttachmentEnum.java, v 0.1 2016年3月19日 下午11:51:44 liguogang Exp $
 */
public enum CommonAttachmentEnum {
    SUCCESS("0", "操作成功", false, true),
    SYSETM_ERROR("1", "系统未知错误", true, false),
    PERMISSION_DENIED("2", "没有权限", true, false),
    DATA_ERROR("3", "数据错误", true, false),  // 通用数据错误
    REPEAT_SUBMIT("4", "重复提交", true, false),
    PARAM_ERROR("5", "参数错误", true, false),
    VERSION_ERROR("6", "版本失效", true, false),
    DATA_MISS("7","数据缺失", true,false),
    DATA_STATUS_ERROR("8","数据状态不正确", true,false),
    CACHE_WRITE_ERROR("9", "写如redis缓存失败", true, false),
    CACHE_READ_ERROR("10", "读取Redis缓存失败", true, false),
    DUMPLICATE_INJECT("101", "重复注入", true, false),
    UPDATE_PROTECTED("11", "更新保护", true, false),  // 数据库批量更新必须指定更新限制的行数
    DATA_DUMPLICATE("12", "数据重复", true, false),
    QUANTITY_BEYOND("13", "数量超过最大限制", true, false),
    SIGN_ERROR("14", "签名错误", true, false), // 签名错误
    NO_TOKEN("15", "缺少Token", true, false), //
    TOKEN_TIMEOUT("16", "重复提交", true,false)//
    ;

    protected final ResultAttachment attachment;

    private CommonAttachmentEnum(String code, String desc, boolean isInterrupt, boolean isSuccess) {
        attachment = new ResultAttachment();
        attachment.setCode(code);
        attachment.setDesc(desc);
        attachment.setInterrupt(isInterrupt);
        attachment.setSuccess(isSuccess);
    }

    public final ResponseAttachment getAttachment() {
        return this.attachment;
    }
}
