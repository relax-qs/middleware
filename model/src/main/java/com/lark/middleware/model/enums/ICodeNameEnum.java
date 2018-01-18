package com.lark.middleware.model.enums;

/**
 * Created by houenxun on 16/4/28.
 * 拥有整型code和字符名称组成的枚举接口
 */
public interface ICodeNameEnum {
    /**
     * 返回状态编码
     * @return
     */
    int getCode();

    /**
     * 返回状态名称
     */

    String getName();


}
