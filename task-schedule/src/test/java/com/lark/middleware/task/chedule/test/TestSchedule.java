package com.lark.middleware.task.chedule.test;

import com.lark.middleware.task.schedule.api.ITaskSchedule;
import com.lark.middleware.task.schedule.api.ScheduleFactory;
import com.lark.middleware.task.schedule.api.TaskHandlerFactory;
import com.lark.middleware.task.schedule.core.DefaultTaskSchedule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by houenxun on 16/8/23.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-schedule.xml" })
public class TestSchedule {
    @Autowired
    DefaultTaskSchedule taskSchedule;

    @org.junit.Test
    public void test() throws Exception{
        Thread.sleep(10000L);

        for(ITaskSchedule schedule : ScheduleFactory.getSchedules()){
            schedule.stop();
        }
    }

}
