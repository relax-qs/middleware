package com.lark.middleware.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by houenxun on 16/5/13.
 */
public class ExceptionUtil {
    /**
     * 获取异常堆栈信息
     *
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable) {
        try (
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
        ) {
            throwable.printStackTrace(pw);
            return sw.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
