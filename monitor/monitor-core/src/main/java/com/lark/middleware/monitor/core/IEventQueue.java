package com.lark.middleware.monitor.core;

import java.util.List;

/**
 * Created by houenxun on 2017/3/27.
 */
public interface IEventQueue<T> {
    /**
     * 初始化当前线程
     */
    void init();

    /**
     * 在当前线程中添加
     *
     * @return
     */
    int add(T t);

    /**
     * 获取当前线程说有的
     *
     * @return
     */
    List<T> getAll();

    /**
     * 清理
     */
    void clear();


}
