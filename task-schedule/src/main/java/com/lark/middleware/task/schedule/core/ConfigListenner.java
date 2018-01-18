package com.lark.middleware.task.schedule.core;

import com.lark.config.client.listerner.DefaultInstanceListener;
import com.lark.config.common.subscribe.event.ConfigGroupEvent;
import com.lark.middleware.task.schedule.api.ITaskSchedule;
import com.lark.middleware.task.schedule.api.TaskScheduleConfig;

/**
 * Created by houenxun on 2016/11/16.
 */
public class ConfigListenner extends DefaultInstanceListener {

    protected ITaskSchedule taskSchedule;
    public ConfigListenner(ITaskSchedule taskSchedule, TaskScheduleConfig target , String groupName){
        super(target, groupName);
        taskSchedule =  taskSchedule;
        this.register();
    }

    @Override
    public boolean notify(ConfigGroupEvent event) {
        boolean result =  super.notify(event);
        if(result){
            taskSchedule.restart();
        }
        return result;
    }
}
