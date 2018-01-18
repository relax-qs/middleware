package com.lark.middleware.model.param;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by houenxun on 16/7/11.
 * 自动校验参数
 */

@Target({METHOD})
@Retention(RUNTIME)
public @interface CheckParam {
    /**
     * 是否自动返回错误信息
     * @return
     */
    boolean autoRespone() default  true;
}
