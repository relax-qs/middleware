package com.lark.middleware.util;

import java.util.Date;
import java.util.Random;

/**
 * Created by houenxun on 16/8/30.
 * 订单编号创建工具
 */
public class OrderNoUtil {
    private static Random random = new Random();
    /**
     * 创建16位订单号 yyMMddhhmm%5BaseId%3random
     * @return
     */
    public static String create16OrderNO(Long baseId){
        String dateStr = DateUtil.getDate(new Date(), "yyMMddHHmm");
        String orderNo = String.format("%s%05d%03d", dateStr, baseId, random.nextInt(1000));
        return orderNo;
    }

    /**
     * 创建32位订单号 yyMMddhhmm%5BaseId%3random
     * @return
     */
    public static String create32OrderNO(Long baseId){
        String dateStr = DateUtil.getDate(new Date(), "yyyyMMddHHmmssSSS");
        String orderNo = String.format("%s%010d%09d", dateStr, baseId, random.nextInt(10000000));
        return orderNo;
    }

    public static void main(String [] args){
       System.out.println(create32OrderNO(1L)) ;
    }
}
