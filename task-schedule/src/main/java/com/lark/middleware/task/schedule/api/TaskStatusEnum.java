package com.lark.middleware.task.schedule.api;

import com.lark.middleware.model.enums.IStatusEnum;

/**
 * Created by houenxun on 16/8/22.
 */
public enum  TaskStatusEnum  implements IStatusEnum {
    init(0, "未执行"),
    success(1, "执行成功"),
    failed(2, "执行失败"),
    fatal(3, "致命错误"), // 致命错误程序无法自动恢复的错误
    delayed(4, "任务被推迟"),
    pause(5, "任务被暂停"),
    stop(6,"任务被停止"),
    ;
    private int code;
    private String name;

    private TaskStatusEnum( int code , String name){
        this.code = code;
        this.name = name;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }


}
