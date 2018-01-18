package com.lark.middleware.task.schedule.api.result;

import com.lark.middleware.task.schedule.api.ITask;

/**
 * Created by houenxun on 16/8/29.
 */
public interface ITaskCreator {
    /**
     * 创建任务
     * @param task
     * @return
     */
    Long createTask(ITask task);

    /**
     * 创建简单任务的接口
     * @param type
     * @param bizIdentify
     * @param hashCode
     * @return
     */
    Long createSimpleTask(String type, String bizIdentify, Integer hashCode);

    /**
     * 如果任务不存在（根据type和bizIdentify），则创建任务，如果已存在则返回已存在的任务id
     * @param task
     * @return
     */
    Long createTaskIfAbsent(ITask task);


    /**
     * 如果任务不存在（根据type和bizIdentify），则创建任务，如果已存在则返回已存在的任务id
     * @param type
     * @param bizIdentify
     * @param hashCode
     * @return
     */
    Long createSimpleTaskIfAbsent(String type, String bizIdentify, Integer hashCode);

}
