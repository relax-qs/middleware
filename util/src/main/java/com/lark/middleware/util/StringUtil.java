package com.lark.middleware.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isNullOrBlank(String... strings) {
        for (String string : strings) {
            if (string == null || string.trim().equals("")) {
                return true;
            }
        }
        return false;
    }

    public static String absentDefault(String main, String substitute) {
        return isNullOrBlank(main) ? substitute : main;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

}
