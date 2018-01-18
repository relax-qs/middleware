package com.lark.middleware.task.schedule.core;

import com.lark.middleware.task.schedule.api.ITask;
import com.lark.middleware.task.schedule.api.ITaskPool;
import com.lark.middleware.task.schedule.api.TaskStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by houenxun on 16/8/22.
 */
public class DefaultTaskPool implements ITaskPool {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Map<Long, LockedTask> catchedTaskMap = new ConcurrentHashMap<>();
    protected StatisticalInfo statisticalInfo = new StatisticalInfo();


    /**
     * 是否是运行状态
     */
    volatile AtomicBoolean running = new AtomicBoolean(false);

    @Override
    public void addTask(ITask task) {
        statisticalInfo.addLoad();
        catchedTaskMap.put(task.getId(), new LockedTask(task));
    }

    @Override
    public void removeTask(ITask task) {
        if (task.getStatus() == TaskStatusEnum.success.getCode()) {
            statisticalInfo.addSuccess();
        } else if (task.getStatus() == TaskStatusEnum.failed.getCode()) {
            statisticalInfo.addFailed();
        }
        catchedTaskMap.remove(task.getId());
    }

    @Override
    public void stop() {
        if (!running.getAndSet(false)) {
            logger.warn("task pool 已停止");
            return;
        }
        statisticalInfo.stop();
    }

    @Override
    public void start() {
        if (running.getAndSet(true)) {
            logger.warn("task pool 已启动");
            return;
        }
        statisticalInfo.start();

    }

    @Override
    public int size() {
        return catchedTaskMap.size();
    }

    @Override
    public Set<Long> getIds() {
        return catchedTaskMap.keySet();
    }


}
