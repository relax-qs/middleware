package com.lark.middleware.monitor.common;

/**
 * Created by houenxun on 2017/3/24.
 */
public class MonitorEvent {
    protected MonitorPoint point;
    protected Long startTime;
    protected Long endTime;

    public MonitorPoint getPoint() {
        return point;
    }

    public void setPoint(MonitorPoint point) {
        this.point = point;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
