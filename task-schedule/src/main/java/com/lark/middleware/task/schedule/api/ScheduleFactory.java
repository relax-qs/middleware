package com.lark.middleware.task.schedule.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houenxun on 16/9/28.
 */
public class ScheduleFactory  {
    private static List<ITaskSchedule> schedules = new ArrayList<ITaskSchedule>();

    /**
     * 注册调度器
     * @param schedule
     */
    public static void register(ITaskSchedule schedule){
        schedules.add(schedule);
    }

    /**
     * 获取调度器
     * @return
     */
    public static List<ITaskSchedule> getSchedules(){
        return schedules;
    }
}
