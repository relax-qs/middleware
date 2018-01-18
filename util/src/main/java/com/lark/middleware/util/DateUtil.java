package com.lark.middleware.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/***
 * 
 * 
 * @author liguogang
 * @version $Id: DateUtil.java, v 0.1 2016年5月26日 下午2:25:46 liguogang Exp $
 */
public class DateUtil {

    //基准时间 2016-05-26 
    public static final Long BASE_TIME           = 1464192000000L;

    public static final Long ONE_DATE_MILLSECOND = 24L * 60 * 60 * 1000L;

    /**
     * @param date
     *            自定义时间
     * @param format
     *            自定义格式
     * @return 返回格式化的日期串
     */
    public static String getDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * @param date
     *            自定义时间
     * @return 返回格式化的日期串 yyyy-MM-dd
     */
    public static String getDate(Date date) {
        return getDate(date, "yyyy-MM-dd");
    }

    /**
     *
     * @param date
     * @return 返回格式化的日期串 HH:mm:ss SSS
     */
    public static String getTime(Date date) {
        return getDate(date, "HH:mm:ss SSS");
    }

    /**
     * @param date
     *            自定义时间
     * @return 返回格式化的日期串 yyyy-MM-dd HH:mm:ss SSS
     */
    public static String getDateTime(Date date) {
        return getDate(date, "yyyy-MM-dd HH:mm:ss SSS");
    }

    /**
     * @param source
     *            日期串
     * @param sdf
     *            格式化工具
     * @return 日期
     */
    public static Date parse(String source, SimpleDateFormat sdf) {
        try {
            return sdf.parse(source);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * @param date
     *            （yyyy-MM-dd）
     * @return 日期
     */
    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * @param dateTime
     *            （yyyy-MM-dd HH:mm:ss SSS）
     * @return 日期
     */
    public static Date parseDateTime(String dateTime) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").parse(dateTime);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date addDays(Date date, int days) {
        Calendar cNow = Calendar.getInstance();
        cNow.setTime(date);
        cNow.add(Calendar.DATE, days);
        return new Date(cNow.getTimeInMillis());
    }

    public static Date addMinutes(Date date, int minutes) {
        Calendar cNow = Calendar.getInstance();
        cNow.setTime(date);
        cNow.add(Calendar.MINUTE, minutes);
        return new Date(cNow.getTimeInMillis());
    }

    public static String parseDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String str = sdf.format(date);
        return str;
    }

    /***
     * 相对于基准时间的偏移量
     * 
     * @return
     */
    public static long dateOffset() {
        return (System.currentTimeMillis() - BASE_TIME) / ONE_DATE_MILLSECOND;
    }

    /**
     * 计算时间天日偏移量
     * @param redisStartTime
     * @param date
     * @return
     * @throws ParseException
     */
    public static Long getTimeOffSetByDate(Date redisStartTime, Date date) throws ParseException {
        if (null != date && null != redisStartTime) {
            Long offSetStamp = date.getTime() - redisStartTime.getTime();
            // 一天为24小时,一小时3600秒,一秒1000毫秒
            Long offSetOfDate = offSetStamp / (1000l * 3600l * 24l);
            return offSetOfDate;
        }
        return null;
    }

    /***
     * 是否为有效的时间，只要在begin和end之间则为true
     * 
     * @param target
     * @param begin
     * @param end
     * @return
     */
    public static boolean isValidate(Date target, Date begin, Date end) {
        if (null == target) {
            return false;
        }

        if (begin != null && target.getTime() < begin.getTime()) {
            return false;
        }

        if (null != end && target.getTime() > end.getTime()) {
            return false;
        }

        return true;
    }
}
