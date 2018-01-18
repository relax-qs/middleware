package com.lark.middleware.model.observer;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by houenxun on 16/9/11.
 * 通知门面类
 */
public class ObserverFacade {
    static Logger logger = LoggerFactory.getLogger(ObserverFacade.class);

    /**
     * 通知所有观察者,任何一个出现问题,中断并抛出异常
     *
     * @param event
     * @param <T>
     */
    public static final <T> void notifyAll(T event) {
        List<IObserver> observers = ObserverFactory.getObservers(event);
        if (CollectionUtils.isNotEmpty(observers)) {
            for (IObserver observer : observers) {
                observer.notify(event);
            }
        } else {
            logger.warn("没有任何监听者, event={}", event);
        }
    }

    /**
     * 通知所有的观察者,不管是否有观察者出现问题,所有的都会受到通知,问题会被忽略
     *
     * @param event
     * @param <T>
     */
    public static final <T> void notifyAllSilence(T event) {
        List<IObserver> observers = ObserverFactory.getObservers(event);
        if (CollectionUtils.isNotEmpty(observers)) {
            for (IObserver observer : observers) {
                try {
                    observer.notify(event);
                } catch (Throwable t) {
                    logger.error("通知异常,event={}, observer={}", event, observer, t);
                }
            }
        } else {
            logger.warn("没有任何监听者, event={}", event);
        }
    }

}
