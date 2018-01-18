package com.lark.middleware.task.schedule.api;

/**
 * Created by houenxun on 16/8/23.
 */
public interface ITaskSchedule extends IComponent {
    /**
     * 注册调度器到工厂类
     */
    default void register(){
        ScheduleFactory.register(this);
    }
}
