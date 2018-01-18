package com.lark.middleware.task.schedule.core;

import com.lark.middleware.task.schedule.api.ICollectorContext;

import java.util.List;

/**
 * Created by houenxun on 16/8/23.
 */
public class DefaultCollectorContext implements ICollectorContext {


    /**
     * 获取当前机器需要处理的任务对应的hashCode
     */
    private List<Integer> currentHashCodes;
    /**
     * 获取最大执行次数
     *
     * @return
     */
    private Integer maxExecCount = TaskScheduleConstants.DEFAULT_MAX_EXEC_COUNT;


    /**
     * 允许执行的ip
     */
    private List<String> execIps;


    /**
     * 返回不允许执行的ip列表,返回null或者空表示不限定,即执行机器黑名单
     *
     * @return
     */
    private List<String> nonExecIps;

    /**
     *
     */
    private List<Long> excludeIds;
    /**
     * 单次拉取数量
     */
    private Integer singleFeatchCount;



    @Override
    public List<Integer> getCurrentHashCodes() {
        return currentHashCodes;
    }

    @Override
    public int getMaxExecCount() {
        return maxExecCount;
    }

    @Override
    public List<String> getExecIps() {
        return execIps;
    }

    @Override
    public List<String> getNonExecIps() {
        return nonExecIps;
    }

    @Override
    public List<Long> getExcludeIds() {
        return excludeIds;
    }

    @Override
    public void setExcludeIds(List<Long> ids) {
        excludeIds = ids;
    }



    public void setCurrentHashCodes(List<Integer> currentHashCodes) {
        this.currentHashCodes = currentHashCodes;
    }

    public void setMaxExecCount(Integer maxExecCount) {
        this.maxExecCount = maxExecCount;
    }

    public void setExecIps(List<String> execIps) {
        this.execIps = execIps;
    }

    public void setNonExecIps(List<String> nonExecIps) {
        this.nonExecIps = nonExecIps;
    }


    public Integer getSingleFeatchCount() {
        return singleFeatchCount;
    }

    public void setSingleFeatchCount(Integer singleFeatchCount) {
        this.singleFeatchCount = singleFeatchCount;
    }
}
