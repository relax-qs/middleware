package com.lark.middleware.monitor.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by houenxun on 2017/3/27.
 */
public class DefaultEventQueue<T> implements IEventQueue<T> {

    ThreadLocal<List<T>> threadLocal;
    @Override
    public void init() {

    }

    @Override
    public int add(T t) {
        List<T> list = threadLocal.get();
        if (null == list) {
            list = new ArrayList<T>();
            threadLocal.set(list);
        }

        list.add(t);
        return list.size();
    }

    @Override
    public List getAll() {
        return threadLocal.get();
    }

    @Override
    public void clear() {
        threadLocal.remove();
    }
}
