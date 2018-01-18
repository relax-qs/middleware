package com.lark.middleware.task.schedule.core;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by houenxun on 16/8/23.
 */
public class ProcessInfo {
    AtomicLong load = new AtomicLong(0); // 载入量
    AtomicLong success = new AtomicLong(0); // 成功数量
    AtomicLong failed = new AtomicLong(0); // 失败数量

    public AtomicLong getLoad() {
        return load;
    }


    public AtomicLong getSuccess() {
        return success;
    }


    public AtomicLong getFailed() {
        return failed;
    }


    void addLoad(){
        load.incrementAndGet();
    }

    void addSuccess(){
        success.incrementAndGet();
    }

    void addFailed(){
        failed.incrementAndGet();
    }

    @Override
    public String toString() {
        return "ProcessInfo{" +
                "load=" + load +
                ", success=" + success +
                ", failed=" + failed +
                '}';
    }
}
