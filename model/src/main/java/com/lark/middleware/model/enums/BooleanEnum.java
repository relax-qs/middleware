package com.lark.middleware.model.enums;

/**
 * Created by houenxun on 16/5/24.
 */

/**
 * 真假枚举
 */
public enum BooleanEnum implements ICodeNameEnum {
    TRUE(1, "真"),
    FALSE(2, "假");


    private BooleanEnum(int code , String name){
        this.code = code;
        this.name = name;
    }

    private int code;
    private String name;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}
