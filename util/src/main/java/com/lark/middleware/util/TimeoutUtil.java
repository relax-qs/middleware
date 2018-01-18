package com.lark.middleware.util;

/**
 * Created by houenxun on 16/5/31.
 * 超时时间工具类
 */
public class TimeoutUtil {
    /**
     * 传入分钟
     * @param len
     * @return
     */
    public static String getTimeOutStr(int len) {
        if (len / (60 * 24) > 1) {
            return len / (60 * 24) + "天";
        } else if (len / 60 > 1) {
            return len / 60 + "小时";
        } else {
            return len + "分钟";
        }

    }
}
