package com.lark.middleware.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by houenxun on 16/5/17.
 * 标识符,生成工具
 */
public class IdentifyGenUtil {
    public static String generate(Long id, String token, String format){
        DateFormat format1 = new SimpleDateFormat(format);
        return token + format1.format(new Date()) + id;
    }
}
