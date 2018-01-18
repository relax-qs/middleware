package com.lark.middleware.task.schedule.api;


import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by houenxun on 16/8/22.
 * 任务
 */
public interface ITask{
    /**
     * 获取id
     * @return
     */
    Long getId();

    String getType();
    /**
     * 获取已执行的次数
     * @return
     */
    Integer getExecCount();

    /**
     * 获取状态
     * @return
     */
    Integer getStatus();

    /**
     * 设置消息
     * @param status
     */
    void setStatus(Integer status);

    /**
     *
     */
    void setExecCount(Integer count);


    /**
     * 设置执行机器的ip
     * @param ip
     */
    void setExecIp(String ip);

    /**
     * 下次执行时间
     * @param time
     */
    void setNextExecTime(Date time);

    /**
     * 根据当前时间,设置延期执行的时间
     * @param duration
     * @param unit
     */
    default void setNextExecTime(long duration, TimeUnit unit){
        Date now = new Date();
        long millseconds = TimeUnit.MILLISECONDS.convert(duration, unit);
        this.setNextExecTime(new Date(now.getTime()+ millseconds));
    }

    /**
     * 设置错误码
     * @param code
     * @return
     */
    void setErrorCode(String code);

    /**
     * 设置错误详情
     * @param desc
     * @return
     */
    void setErrorDetail(String desc);

    /**
     * 获取业务标识
     * @return
     */
    String getBizIdentify();

    /**
     * 获取创建任务的ip
     * @return
     */
    String getCreatorIp();

    /**
     *  设置创建任务的ip
     */
    void setCreatorIp(String ip);




}
