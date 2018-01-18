/**
 * hnlark.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.lark.middleware.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/***
 * 随机数生成工具
 * 
 * @author liguogang
 * @version $Id: RandomUtil.java, v 0.1 2016年10月25日 下午12:40:26 liguogang Exp $
 */
public class RandomUtil {
    // 随机数
    private final static Random                  random             = new Random();

    // 随机字母池
    private final static Character               C_EDEN[]           = new Character[] { 'A', 'Z', '2', 'B', '8', 'Y', '0', 'D', 'X', 'E', 'G', '1', 'V', 'H',
                                                                                        '9', 'I', 'U', 'J', 'K', '3', 'L', 'N', '4', 'O', 'P', '5', 'Q', 'R',
                                                                                        '6', 'S', 'T', 'M', 'F', 'W', '7', 'C' };
    // 随机池大小
    private final static int                     C_EDEN_LEN         = C_EDEN.length;

    // 池子 字母和 下标的映射
    private final static Map<Character, Integer> EDEN_INDEX_MAP     = new HashMap<>();

    // 校验和开始位置
    private final static int                     VALIDATE_SUM_BEGIN = C_EDEN_LEN / 2;

    static {
        init();
    }

    private static void init() {
        for (int i = 0; i < C_EDEN_LEN; i++) {
            Character c = C_EDEN[i];
            EDEN_INDEX_MAP.put(c, i);
        }
    }

    /**
     * 获取随机位数的字符串
     * @return
     */
    public static String getRandomNum(Integer x) {
        String n = "";
        int getNum;
        do {
            getNum = Math.abs(random.nextInt()) % 10 + 48;//产生数字0-9的随机数
            char num1 = (char) getNum;
            String dn = Character.toString(num1);
            n += dn;
        } while (n.length() < x);
        return n;
    }

    /***
     * 随机生成字符串
     * 
     * @param num
     * @return
     */
    public static String random(int num) {
        if (num <= 0) {
            return null;
        }
        if (num == 1) {
            int index = random.nextInt(C_EDEN_LEN);
            return C_EDEN[index].toString();
        }

        char tmpC[] = new char[num];
        int validateSum = VALIDATE_SUM_BEGIN;
        for (int i = 0, j = num - 1; i < j; i++) {
            Character character = C_EDEN[random.nextInt(C_EDEN_LEN)];
            tmpC[i] = character;
            validateSum += fetchValidateNum(character, i);
        }
        tmpC[num - 1] = fetchValidateChar(validateSum);
        return new String(tmpC);
    }

    public static boolean validate(String random) {
        if (null == random || random.length() <= 1) {
            return true;
        }

        char c[] = random.toCharArray();
        int validateSum = VALIDATE_SUM_BEGIN;
        for (int i = 0, j = random.length() - 1; i < j; i++) {
            validateSum += fetchValidateNum(c[i], i);
        }

        return c[random.length() - 1] == fetchValidateChar(validateSum);
    }

    /***
     * 获取校验位
     * 
     * @param validateSum
     * @return
     */
    public static Character fetchValidateChar(int validateSum) {
        int index = validateSum % C_EDEN_LEN;
        return C_EDEN[index];
    }

    /***
     * 获取校验和
     * 
     * @param c
     * @param index
     * @return
     */
    public static int fetchValidateNum(Character c, int index) {
        return EDEN_INDEX_MAP.get(c) * (index + VALIDATE_SUM_BEGIN);
    }
}
