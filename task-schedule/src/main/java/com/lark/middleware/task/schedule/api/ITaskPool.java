package com.lark.middleware.task.schedule.api;

import java.util.Date;
import java.util.Set;

/**
 * Created by houenxun on 16/8/22.
 * 执行中的任务池
 */
public interface ITaskPool extends IComponent{
    /**
     * 添加任务
     * @param task
     * @return
     */
    void addTask(ITask task);


    /**
     * 执行完成后移除任务
     * @param task
     */
    void removeTask(ITask task);

    int size();


    Set<Long> getIds();


    public class LockedTask {
        private Long taskId; //任务id
        private Date lockDate; // 锁定时间
        private ITask task;
        private Thread t;


        public LockedTask(ITask task){
            this.taskId = task.getId();
            this.lockDate = new Date();
            this.task = task;
            t = Thread.currentThread();
        }
    }
}
