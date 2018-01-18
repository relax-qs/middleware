package com.lark.middleware.task.schedule.core;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by houenxun on 16/8/22.
 */
public class NextExecTimeCalculator {
    /**
     * 根据已执行的次数计算下次执行时间
     * @param count
     * @return
     */
    public Date calculateNextExecTime(int count){
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        switch (count){
            case 0: // 立即执行
                return now;
            case 1: // 三分钟执行
                calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE)+ 3);
                return calendar.getTime();
            case 2: // 15分钟执行
                calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE)+ 15);
                return calendar.getTime();
            case 3: // 1小时执行
                calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR)+ 1);
                return calendar.getTime();
            case 4: // 3个小时后执行
                calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR)+ 3);
                return calendar.getTime();
            default: // 1天后执行
                calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+ 1);
                return calendar.getTime();
        }

    }
}
