/**
 * hnlark.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.lark.middleware.util;

/**
 * sql脚本工具类
 * @author MOUBK
 * @version $Id: SqlUtil.java, v 0.1 2016年5月3日 下午12:06:39 MOUBK Exp $
 */
public class SqlUtil {
    /**
     * 获取模糊查询left like 字符串
     * @param value
     * @return
     */
    public static String getLeftLike(String value) {
        if (value == null) {
            return value;
        }
        StringBuffer temp = new StringBuffer();
        temp.append("%").append(value);
        return temp.toString();
    }

    /**
     * 获取模糊查询right like 字符串
     * @param value
     * @return
     */
    public static String getRightLike(String value) {
        if (value == null) {
            return value;
        }
        StringBuffer temp = new StringBuffer();
        temp.append(value).append("%");
        return temp.toString();
    }

    /**
     * 获取模糊查询all like 字符串
     * @param value
     * @return
     */
    public static String getAllLike(String value) {
        if (value == null) {
            return value;
        }
        StringBuffer temp = new StringBuffer();
        temp.append("%").append(value).append("%");
        return temp.toString();
    }
}
