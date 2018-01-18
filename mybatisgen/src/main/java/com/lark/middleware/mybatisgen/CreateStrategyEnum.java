package com.lark.middleware.mybatisgen;

/**
 * Created by houenxun on 16/4/14.
 */
public enum CreateStrategyEnum {
    never("never", "不会创建"),
    ifNot("ifNot", "如果不存在就创建"),
    always("always", "永远创建");
    private String code;
    private String desc;

    private CreateStrategyEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;

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
