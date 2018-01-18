package com.lark.middleware.task.schedule.core;

import com.lark.middleware.model.BusinessException;
import com.lark.middleware.task.schedule.api.*;
import com.lark.middleware.task.schedule.api.result.DelayHandleResult;
import com.lark.middleware.task.schedule.api.result.ErrorHandleResult;
import com.lark.middleware.task.schedule.api.result.IHandleResult;
import com.lark.middleware.task.schedule.api.result.SuccessHandleResult;
import com.lark.middleware.util.ExceptionUtil;
import com.lark.middleware.util.IpUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by houenxun on 16/8/22.
 */
public class RunnableTask implements Runnable {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected ITask task;
    protected ITaskPool taskPool;
    protected NextExecTimeCalculator nextExecTimeCalculator;
    protected ITaskUpdator taskUpdator;
    protected TaskScheduleConfig taskScheduleConfig;

    public RunnableTask(ITask task, ITaskPool taskPool, NextExecTimeCalculator nextExecTimeCalculator, ITaskUpdator taskUpdator, TaskScheduleConfig taskScheduleConfig) {
        this.task = task;
        this.taskPool = taskPool;
        this.nextExecTimeCalculator = nextExecTimeCalculator;
        this.taskUpdator = taskUpdator;
        this.taskScheduleConfig = taskScheduleConfig;
        taskPool.addTask(task);

    }

    public ITask getTask() {
        return task;
    }

    @Override
    public void run() {
        this.process(task);
        taskPool.removeTask(task);
    }

    public boolean process(ITask task) {
        if (null == nextExecTimeCalculator) {
            nextExecTimeCalculator = new NextExecTimeCalculator();
        }
        try {
            ITaskHandler handler = TaskHandlerFactory.getTaskHandler(task.getType());
            if (null == handler) {
                logger.error("当前任务找不到处理器task={}", task);
                task.setStatus(TaskStatusEnum.fatal.getCode());
                task.setErrorCode("找不到处理器");
                return false;
            }
            IHandleResult result = handler.handle(task);
            /**
             * 处理成功
             */
            if (null == result || result instanceof SuccessHandleResult) {
                task.setStatus(TaskStatusEnum.success.getCode());
            } else if (result instanceof DelayHandleResult) { /** 任务被推迟**/
                handleDelayReturn(task, (DelayHandleResult) result);

            } else if (result instanceof ErrorHandleResult) {
                ErrorHandleResult errorHandleResult = (ErrorHandleResult) result;
                task.setStatus(TaskStatusEnum.failed.getCode());
                task.setErrorCode(StringUtils.substring(errorHandleResult.getErrorCode(), 0, taskScheduleConfig.getMaxErrorCodeLength()));
                task.setErrorDetail(StringUtils.substring(errorHandleResult.getErrorDesc(), 0, taskScheduleConfig.getMaxErrorDetailLength()));
            }


        } catch (BusinessException e) {
            logger.error("处理任务失败task={}", task, e);
            task.setStatus(TaskStatusEnum.failed.getCode());
            task.setErrorCode(StringUtils.substring(e.getAttachment().getCode(), 0, taskScheduleConfig.getMaxErrorCodeLength()));
            task.setErrorDetail(StringUtils.substring(ExceptionUtil.getStackTrace(e), 0, taskScheduleConfig.getMaxErrorDetailLength()));
        } catch (Throwable t) {
            logger.error("处理任务失败", t);
            task.setStatus(TaskStatusEnum.failed.getCode());
            task.setErrorCode(StringUtils.substring(t.getClass().getName(), 0, taskScheduleConfig.getMaxErrorCodeLength()));
            task.setErrorDetail(StringUtils.substring(ExceptionUtil.getStackTrace(t), 0, taskScheduleConfig.getMaxErrorDetailLength()));
        } finally {
            /**
             * 已执行的次数增加1
             */
            task.setExecCount(task.getExecCount() + 1);
            task.setExecIp(IpUtil.getLocalIp());
            /**
             * 执行失败计算下一次更新时间
             */
            if (task.getStatus() == TaskStatusEnum.failed.getCode() || task.getStatus() == TaskStatusEnum.fatal.getCode())
                task.setNextExecTime(nextExecTimeCalculator.calculateNextExecTime(task.getExecCount()));
            try {
                taskUpdator.updateTask(task);
            } catch (Throwable t) {
                logger.error("更新任务失败 task={}", task, t);
            }
        }

        return false;
    }

    /**
     * 处理延后的请求
     *
     * @param task
     * @param result
     */
    private void handleDelayReturn(ITask task, DelayHandleResult result) {
        DelayHandleResult delayHandleResult = result;
        if (null == delayHandleResult.getTimeUnit() || null == delayHandleResult.getDelay()) {
            delayHandleResult.setDelay(taskScheduleConfig.getDefaultDelayTime(), TimeUnit.SECONDS);
        }
        /**
         * 计算再次执行的日期
         */
        task.setNextExecTime(delayHandleResult.getDelayedExecTime());
        task.setStatus(TaskStatusEnum.delayed.getCode());
        task.setErrorCode(null);
        task.setErrorDetail(delayHandleResult.getDelayReason());
        /**
         * 本次不计入执行次数
         */
        task.setExecCount(task.getExecCount() - 1);
    }
}
