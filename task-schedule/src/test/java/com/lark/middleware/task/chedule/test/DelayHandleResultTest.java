package com.lark.middleware.task.chedule.test;

import com.lark.middleware.task.schedule.api.result.DelayHandleResult;
import org.junit.*;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by houenxun on 16/8/24.
 */
public class DelayHandleResultTest {
    @Test
    public void testCalendarAddSecond(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.SECOND, 60 +3);  // 1分钟 3秒

        Date newDate = calendar.getTime();

        Assert.assertTrue((date.getSeconds() + 3)%60 == newDate.getSeconds());

        Assert.assertTrue((date.getMinutes() + 1 + (date.getSeconds() + 3)/ 60 )%60 == newDate.getMinutes());

    }
    @Test
    public void testDelay(){
        // 延期101 分钟
        Date date = new Date();
        DelayHandleResult result = new DelayHandleResult(101L, TimeUnit.MINUTES);

        Date newDate = result.getDelayedExecTime();


        Assert.assertTrue((newDate.getTime() - date.getTime())/1000/60 == 101);


    }
}
