package com.lark.middleware.model.convertor;

import org.springframework.beans.factory.InitializingBean;

/**
 * Created by houenxun on 16/4/27.
 * 数据转换接口
 */
public interface IDataConvertor<S, T> extends InitializingBean {
    /**
     * 创建并转换对象
     * @param s
     * @return
     */
    T convertor(S s);

    /**
     * 转换已存在的对象,对于空置默认转换
     * @param s
     * @param t
     * @return
     */
    T convertor(S s, T t);

    /**
     * 注册
     */
    default void register(){
        DataConvertorFactory.register(this);
    }

    Class<S> getSourceClass();
    Class<T> getTargetClass();

    default void afterPropertiesSet() throws Exception {
        this.register();
    }

}
