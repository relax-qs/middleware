package com.lark.middleware.task.schedule.core;

/**
 * Created by houenxun on 16/8/22.
 */
public interface TaskScheduleConstants {
    public static final int DEFAULT_WAIT_TIME_IF_MAX_QUEUE = 1000;
    public static final int DEFAULT_WAIT_TIME_IF_MIN_QUEUE = 0;
    public static final int DEFAULT_PROCESS_THREAD_COUNT = 8;

    public static final int DEFAULT_SINGLE_FEATCH_COUNT = 100;
    public static final int DEFAULT_MAX_QUEUE =  1000;
    public static final int DEFAULT_MIN_QUEUE = 100;

    public static final int DEFAULT_WAIT_TIME = 200;
    public static final int DEFAULT_WAIT_TIME_IF_NO_TASK = 1000;

    public static final int DEFAULT_MAX_EXEC_COUNT = 5;


    public static final int DEFAULT_DELAY_TIME = 60*5; // 默认延期五分钟

    public static final int DEFAULT_WAIT_TIME_ON_START = 10; // 启动时的默认等待时间 默认10秒

    public static final int DEFAULT_MAX_ERROR_DETAIL_LENGTH = 1024;  // 错误详情的最大长度

    public static final int DEFAULT_MAX_ERROR_CODE_LENGTH = 64;  // 错误详情的最大长度

}
