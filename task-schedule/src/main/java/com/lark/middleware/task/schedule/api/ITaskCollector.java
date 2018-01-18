package com.lark.middleware.task.schedule.api;

import java.util.List;

/**
 * Created by houenxun on 16/8/22.
 * 任务收集器
 */
public interface ITaskCollector {
    /**
     * 拉取任务
     * @param context
     * @return
     */
    List<ITask> collectTasks(ICollectorContext context);



}
