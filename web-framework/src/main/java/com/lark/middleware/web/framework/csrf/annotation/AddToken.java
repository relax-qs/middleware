package com.lark.middleware.web.framework.csrf.annotation;

/**
 * Created by houenxun on 2017/3/28.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标示接口会返回token
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AddToken {
    /**
     * 超时时间默认使用全局配置
     * @return
     */
    long timeout() default 0;
}
