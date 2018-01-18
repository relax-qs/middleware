package com.lark.middleware.monitor.common;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by houenxun on 2017/3/24.
 * 监控点
 */
@Target({METHOD})
@Retention(RUNTIME)
public @interface MonitorNode {
    /**
     * 监控点的名称
     *
     * @return
     */
    String name() default "";

    /**
     * 相同code的节点，会当成同一个节点进行处理
     *
     * @return
     */
    String code() default "";
}
