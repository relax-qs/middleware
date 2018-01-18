package com.lark.middleware.model.constants;

/**
 * Created by houenxun on 16/4/27.
 * 通用状态常量定义
 */
public final  class StatusConstants {

    public static final int NORMAL = 1 ; // 正常状态
    public static final int DISABLE = 0 ; // 禁用状态

    public static final int DELETED = -1 ; // 已删除

    public static final int SUCCESS = 1; // 处理成功

    public static final int FAILED = 2;   // 失败
    public static final int IGNORED = 3; // 已忽略

    public static final int CONFIRM_SUCCESS = 4; //确认成功

    public static final int FATAL = 5; // 致命错误,必须手动干预不可重复


    public static final int INIT = 0; //未处理, 初始状态

    public static final int PARTLY_SUCCESS = 9; // 部分成功

    public static final int PROCESSING = 10; // 处理中

    /**
     * 禁止创建对象
     */
    private StatusConstants(){

    }
}
