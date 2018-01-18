/**
 * hnlark.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.lark.middleware.model.enums;

/**
 * 
 * @author MOUBK
 * @version $Id: UserStatusEnum.java, v 0.1 2016年4月27日 下午5:38:06 MOUBK Exp $
 */
public enum StatusEnum implements ICodeNameEnum {

    enabled(1, "正常"), disabled(0, "禁用"), delete(-1, "删除"),inactive(2,"未激活");

    private StatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private Integer code;

    private String  name;

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCodeString() {
        return String.valueOf(code);
    }
}
