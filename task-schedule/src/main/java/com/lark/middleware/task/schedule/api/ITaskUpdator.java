package com.lark.middleware.task.schedule.api;

import java.util.concurrent.TimeUnit;

/**
 * Created by houenxun on 16/8/22.
 */
public interface ITaskUpdator {
    /**
     * 根据任务id更新任务， task.getId不允许为空 其他属性为空将不更新
     *
     * @param task
     */
    boolean updateTask(ITask task);

    /**
     * 以当前时间为起点推延任务
     *
     * @param taskId
     * @param duration
     * @param unit
     * @return 有更新返回成功,
     */
    boolean delayTaskFromNow(Long taskId, long duration, TimeUnit unit);


    /**
     * 以任务的执行时间为起点推延任务
     *
     * @param taskId
     * @param duration
     * @param unit
     * @return 有更新返回成功
     */
    boolean delayTaskFromNextExecTime(Long taskId, long duration, TimeUnit unit);


    /**
     * 以当前时间为起点推延任务
     *
     * @param bizType
     * @param bizIdentify
     * @param duration
     * @param unit
     * @return 更新的条数
     */
    int delayTaskFromNow(String bizType, String bizIdentify, long duration, TimeUnit unit);


    /**
     * 以任务的执行时间为起点推延任务
     *
     * @param bizType
     * @param bizIdentify
     * @param duration
     * @param unit
     * @return 更新的条数
     */
    int delayTaskFromNextExecTime(String bizType, String bizIdentify, long duration, TimeUnit unit);

}
