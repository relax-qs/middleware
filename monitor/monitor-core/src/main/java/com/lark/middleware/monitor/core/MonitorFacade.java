package com.lark.middleware.monitor.core;

import com.lark.middleware.monitor.common.MonitorEvent;

import java.util.List;

/**
 * Created by houenxun on 2017/3/27.
 */
public class MonitorFacade {


    static DefaultEventQueue<MonitorEvent> queue = new DefaultEventQueue();

    /**
     * @param code
     */
    public static void start(String code) {


    }

    /**
     * 进入某一个节点
     *
     * @param code
     */
    public static void enter(String code) {


    }

    /**
     * 离开某一个监控点
     *
     * @param code
     */
    public static void leave(String code) {

    }

    /**
     * 清理某一次监控
     */
    public void clear() {
        List<MonitorEvent> eventList = queue.getAll();
        queue.clear();



    }

    /**
     * 发送事件
     *
     * @param event
     */
    public static void delivery(MonitorEvent event) {
        queue.add(event);
    }


}
