package com.lark.middleware.task.schedule.api;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by houenxun on 16/8/22.
 * 任务工厂
 */
public class TaskHandlerFactory {
    static Logger logger = LoggerFactory.getLogger(TaskHandlerFactory.class);
    private static Map<String, ITaskHandler> handlerMap = new HashMap<>();

    /**
     * 注册任务
     *
     * @param type
     */
    public static void register(String type, ITaskHandler handler) {
        if (StringUtils.isBlank(type)) {
            throw new RuntimeException("type is null");
        }
        if (handlerMap.containsKey(type)) {
            logger.error("重复注册ype={}, handler={}", type, handler);
        }

        handlerMap.put(type, handler);
    }

    public static  ITaskHandler getTaskHandler(String type){
        return handlerMap.get(type);
    }

}
