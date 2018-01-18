package com.lark.middleware.task.chedule.test;

import com.lark.middleware.task.schedule.api.ITask;
import com.lark.middleware.util.IpUtil;

import java.util.Date;

/**
 * Created by houenxun on 16/8/23.
 */
public class TestTask implements ITask {
    private Long id;
    private String type;
    private Integer execCount;
    private String execIp;
    private Date nextExecTime;
    private String errorCode;
    private String errorDetail;
    private Integer status;

    private String creatorIp = IpUtil.getLocalIp();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Integer getExecCount() {
        return execCount;
    }

    @Override
    public void setExecCount(Integer count) {
        this.execCount = count;
    }

    @Override
    public void setExecIp(String ip) {
        this.execIp = ip;
    }

    @Override
    public void setNextExecTime(Date time) {
        this.nextExecTime = time;
    }

    @Override
    public void setErrorCode(String code) {
        this.errorCode = errorCode;
    }

    @Override
    public void setErrorDetail(String desc) {
        this.errorDetail = errorDetail;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExecIp() {
        return execIp;
    }

    public Date getNextExecTime() {
        return nextExecTime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    @Override
    public String getBizIdentify() {
        return null;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String getCreatorIp() {
        return creatorIp;
    }

    @Override
    public void setCreatorIp(String ip) {
        creatorIp = ip;
    }

    @Override
    public String toString() {
        return "TestTask{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", execCount=" + execCount +
                ", execIp='" + execIp + '\'' +
                ", nextExecTime=" + nextExecTime +
                ", errorCode='" + errorCode + '\'' +
                ", errorDetail='" + errorDetail + '\'' +
                '}';
    }
}
