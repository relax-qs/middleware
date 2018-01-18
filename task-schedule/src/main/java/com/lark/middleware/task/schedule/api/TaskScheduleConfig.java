package com.lark.middleware.task.schedule.api;

import com.lark.middleware.task.schedule.core.TaskScheduleConstants;
import com.lark.middleware.util.CollectionsUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by houenxun on 16/8/22.
 */
public class TaskScheduleConfig {

    /**
     * 获取需要收集的全部任务对应的hash值
     *
     * @return
     */
    private List<Integer> hashCodes;
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
     * 处理线程数量
     */
    private Integer processThreadCount = TaskScheduleConstants.DEFAULT_PROCESS_THREAD_COUNT;
    /**
     * 任务队列已满时的等待时间
     */
    private Integer waitTimeIfMaxQueu = TaskScheduleConstants.DEFAULT_WAIT_TIME_IF_MAX_QUEUE;

    /**
     * 上一次拉取任务为空的等等时间
     */
    private Integer weitTimeIfNoTask = TaskScheduleConstants.DEFAULT_WAIT_TIME_IF_NO_TASK;
    /**
     * 单次拉取的数量
     */
    private Integer singleFeatchCount = TaskScheduleConstants.DEFAULT_SINGLE_FEATCH_COUNT;
    /**
     * 最大队列长度
     */
    private Integer maxQueueSize = TaskScheduleConstants.DEFAULT_MAX_QUEUE;
    /**
     * 最小队列长度,当队列长度小于minQueueSize时立即拉取
     */
    private Integer minQueueSize = TaskScheduleConstants.DEFAULT_MIN_QUEUE;
    /**
     * 在队列长大于minQueueSize 小于maxQueueSize 每次拉取,sleep defaultWaitTime时间
     */
    private Integer defaultWaitTime = TaskScheduleConstants.DEFAULT_WAIT_TIME;
    /**
     * 默认延期时间 单位是秒
     */
    private Integer defaultDelayTime = TaskScheduleConstants.DEFAULT_DELAY_TIME;
    /**
     * 启动时的默认等待时间
     */
    private Integer waitTimeOnStart = TaskScheduleConstants.DEFAULT_WAIT_TIME_ON_START;
    /**
     * 错误描述信息的最大长度
     */
    private Integer maxErrorDetailLength = TaskScheduleConstants.DEFAULT_MAX_ERROR_DETAIL_LENGTH;
    /**
     * 错误码的最大长度
     */
    private Integer maxErrorCodeLength = TaskScheduleConstants.DEFAULT_MAX_ERROR_CODE_LENGTH;


    public List<Integer> getHashCodes() {
        return hashCodes;
    }

    public void setHashCodes(String hashCodes) {
        if (StringUtils.isNotBlank(hashCodes)) {
            this.hashCodes = CollectionsUtil.parseList(hashCodes, CollectionsUtil.DEFAULT_INTEGER_PARSER, ",");
        } else {
            this.hashCodes = null;
        }
    }

    public void setHashCodes(List<Integer> hashCodes) {
        this.hashCodes = hashCodes;
    }

    public List<Integer> getCurrentHashCodes() {
        return currentHashCodes;
    }

    public void setCurrentHashCodes(List<Integer> currentHashCodes) {
        this.currentHashCodes = currentHashCodes;
    }

    public int getMaxExecCount() {
        return maxExecCount;
    }

    public void setMaxExecCount(Integer maxExecCount) {
        this.maxExecCount = maxExecCount;
    }

    public List<String> getExecIps() {
        return execIps;
    }

    public void setExecIps(List<String> execIps) {
        this.execIps = execIps;
    }

    public void setExecIps(String ips) {
        if (StringUtils.isNotBlank(ips)) {
            execIps = CollectionsUtil.parseList(ips, CollectionsUtil.DEFAULT_STRING_PARSER, ",");
        } else {
            execIps = null;
        }
    }

    public List<String> getNonExecIps() {
        return nonExecIps;
    }

    public void setNonExecIps(String ips) {
        if (StringUtils.isNotBlank(ips)) {
            nonExecIps = CollectionsUtil.parseList(ips, CollectionsUtil.DEFAULT_STRING_PARSER, ",");
        } else {
            nonExecIps = null;
        }
    }

    public void setNonExecIps(List<String> nonExecIps) {
        this.nonExecIps = nonExecIps;
    }

    public Integer getProcessThreadCount() {
        return processThreadCount;
    }

    public void setProcessThreadCount(Integer processThreadCount) {
        this.processThreadCount = processThreadCount;
    }

    public Integer getWaitTimeIfMaxQueu() {
        return waitTimeIfMaxQueu;
    }

    public void setWaitTimeIfMaxQueu(Integer waitTimeIfMaxQueu) {
        this.waitTimeIfMaxQueu = waitTimeIfMaxQueu;
    }

    public Integer getWeitTimeIfNoTask() {
        return weitTimeIfNoTask;
    }

    public void setWeitTimeIfNoTask(Integer weitTimeIfNoTask) {
        this.weitTimeIfNoTask = weitTimeIfNoTask;
    }

    public Integer getSingleFeatchCount() {
        return singleFeatchCount;
    }

    public void setSingleFeatchCount(Integer singleFeatchCount) {
        this.singleFeatchCount = singleFeatchCount;
    }

    public Integer getMaxQueueSize() {
        return maxQueueSize;
    }

    public void setMaxQueueSize(Integer maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    public Integer getMinQueueSize() {
        return minQueueSize;
    }

    public void setMinQueueSize(Integer minQueueSize) {
        this.minQueueSize = minQueueSize;
    }

    public Integer getDefaultWaitTime() {
        return defaultWaitTime;
    }

    public void setDefaultWaitTime(Integer defaultWaitTime) {
        this.defaultWaitTime = defaultWaitTime;
    }

    public Integer getDefaultDelayTime() {
        return defaultDelayTime;
    }

    public void setDefaultDelayTime(Integer defaultDelayTime) {
        this.defaultDelayTime = defaultDelayTime;
    }

    public Integer getWaitTimeOnStart() {
        return waitTimeOnStart;
    }

    public void setWaitTimeOnStart(Integer waitTimeOnStart) {
        this.waitTimeOnStart = waitTimeOnStart;
    }

    public Integer getMaxErrorDetailLength() {
        return maxErrorDetailLength;
    }

    public void setMaxErrorDetailLength(Integer maxErrorDetailLength) {
        this.maxErrorDetailLength = maxErrorDetailLength;
    }

    public Integer getMaxErrorCodeLength() {
        return maxErrorCodeLength;
    }

    public void setMaxErrorCodeLength(Integer maxErrorCodeLength) {
        this.maxErrorCodeLength = maxErrorCodeLength;
    }
}
