package com.lark.middleware.task.schedule.api;

import java.net.InetAddress;
import java.util.List;

/**
 * Created by houenxun on 16/8/22.
 * 收集环境
 */
public interface ICollectorContext {

    /**
     * 获取当前机器需要处理的任务对应的hashCode
     * @return
     */
    List<Integer> getCurrentHashCodes();

    /**
     * 获取最大执行次数
     * @return
     */
    int getMaxExecCount();

    /**
     * 返回限定的机器ip列表,返回null表示不作限定,即执行机器白名单
     * @return
     */
    List<String> getExecIps();

    /**
     * 返回不允许执行的ip列表,返回null或者空表示不限定,即执行机器黑名单
     * @return
     */
    List<String> getNonExecIps();

    List<Long> getExcludeIds();

    void setExcludeIds(List<Long> ids);

    /**
     * 获取单次拉取的数量
     * @return
     */
    Integer getSingleFeatchCount();

}
