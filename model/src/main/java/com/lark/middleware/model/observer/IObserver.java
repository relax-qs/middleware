package com.lark.middleware.model.observer;

import org.springframework.beans.factory.InitializingBean;

/**
 * Created by houenxun on 16/9/11.
 * 监听者
 */
public interface IObserver<T> extends InitializingBean{
    /**
     * 通知监听者,通知失败会直接抛出异常
     * @param event
     */
    void notify(T event);

    @Override
    default void afterPropertiesSet() throws Exception {
        register();
    }

    default void register(){
        ObserverFactory.register(this, getEventType());
    }

    /**
     * 获取监听事件类型
     * @return
     */
    Class<T> getEventType();
}
