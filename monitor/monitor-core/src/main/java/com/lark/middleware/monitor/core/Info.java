package com.lark.middleware.monitor.core;

import com.lark.middleware.monitor.common.MonitorPoint;

/**
 * Created by houenxun on 2017/3/27.
 */
public class Info {
    /**
     * 监控点
     */
    MonitorPoint point;
    /**
     * 第一次时间
     */
    Long firstTime;
    /**
     * 成功调用次数
     */
    long successCount;
    /**
     * 失败调用次数
     */
    long failCount;

    /**
     * 成功消耗时间
     */
    long successTime;
    /**
     *
     */
    long failTime;
}
