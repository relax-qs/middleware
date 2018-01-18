package com.lark.middleware.model.observer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by houenxun on 16/9/11.
 */
public class ObserverFactory {
    static Logger logger = LoggerFactory.getLogger(ObserverFactory.class);


    private static final Map<Class, List<IObserver>> observerMap = new HashMap<>();

    /**
     * 注册观察者
     *
     * @param observer
     * @param T
     * @param <T>
     */
    public static final <T> void register(IObserver observer, Class<T> T) {
        List<IObserver> observers = observerMap.get(T);
        if (null == observers) {
            observers = new ArrayList<>();
            observerMap.put(T, observers);
        }
        if(observers.contains(observer)){
            logger.warn("重复注册,observer={}, T={}", observer, T);
        }else {
            observers.add(observer);
        }

        logger.info("observerMap={}", observerMap);
    }

    /**
     * 根据事件获取监听者列表
     * @param event
     * @param <T>
     * @return
     */
    public static final <T> List<IObserver> getObservers(T event) {
        return observerMap.get(event.getClass());
    }


}
