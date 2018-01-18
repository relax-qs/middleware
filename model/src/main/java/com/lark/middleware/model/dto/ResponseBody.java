package com.lark.middleware.model.dto;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by houenxun on 16/9/10.
 * 是否对返回的数据使用Response 进行封装
 */
@Target({METHOD})
@Retention(RUNTIME)
public @interface ResponseBody {
    boolean vaule() default  true; // 默认进行封装
}
