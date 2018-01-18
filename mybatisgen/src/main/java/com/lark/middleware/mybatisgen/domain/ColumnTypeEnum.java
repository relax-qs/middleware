package com.lark.middleware.mybatisgen.domain;

/**
 * Created by houenxun on 16/4/15.
 */
public enum ColumnTypeEnum {
    normal("normal", "普通"),
    id("id", "主键"),
    version("version", "乐观锁"),
    gmtCreate("gmtCreate", "创建时间"),
    gmtModified("gmtModified", "修改时间"),
        ;
    private String code;
    private String desc;
    private ColumnTypeEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
