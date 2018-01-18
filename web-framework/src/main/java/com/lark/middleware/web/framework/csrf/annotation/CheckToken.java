package com.lark.middleware.web.framework.csrf.annotation;

/**
 * Created by houenxun on 2017/3/28.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标示接口会返回校验token
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckToken {
    /**
     * 校验错误的返回码, 默认使用全局配置
     *
     * @return
     */
    String noTokenCode() default "";

    /**
     * 校验错误的详情, 默认使用全局配置
     *
     * @return
     */
    String noTokenMessage() default "";


    /**
     * 校验错误的返回码, 默认使用全局配置
     *
     * @return
     */
    String tokenExpireCode() default "";

    /**
     * 校验错误的详情, 默认使用全局配置
     *
     * @return
     */
    String tokenExpireMessage() default "";


}
