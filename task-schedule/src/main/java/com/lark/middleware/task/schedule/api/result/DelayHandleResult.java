package com.lark.middleware.task.schedule.api.result;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by houenxun on 16/8/24.
 * 推迟的返回的结果
 */
public class DelayHandleResult implements IHandleResult{
    /**
     * 推迟的时间
     */
    private Long delay ;
    /**
     * 推迟的时间单位
     */
    private TimeUnit timeUnit;
    /**
     * 推迟原因
     */
    private String delayReason;

    /**
     * 系统默认的推迟时间
     */
    public DelayHandleResult(){

    }

    public DelayHandleResult(long delay, TimeUnit timeUnit){
        this(delay, timeUnit, null);
    }

    public DelayHandleResult(long delay, TimeUnit timeUnit, String reason){
        this.delay = delay;
        this.timeUnit = timeUnit;
        this.delayReason = reason;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public void setDelay(long delay, TimeUnit unit) {
        this.delay = delay;
        this.timeUnit = unit;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String getDelayReason() {
        return delayReason;
    }

    public void setDelayReason(String delayReason) {
        this.delayReason = delayReason;
    }

    public Date getDelayedExecTime(){
        Date now = new Date();
        if(null == timeUnit || null == delay){
            return now;
        }
        /**
         * 计算延迟后的执行时间getTime返回的是毫秒
         */
        return new Date(now.getTime() + TimeUnit.MILLISECONDS.convert(delay, timeUnit));
    }
}
