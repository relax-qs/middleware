package com.lark.middleware.task.schedule.api;

/**
 * Created by houenxun on 16/8/23.
 * 主键
 */
public interface IComponent {

    void stop();
    void start();
    default  void  restart(){
        stop();
        start();
    }


}
