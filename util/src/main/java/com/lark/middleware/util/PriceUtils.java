package com.lark.middleware.util;

/**
 * hnlark.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */

import java.math.BigDecimal;

/**
 * 
 * @author MOUBK
 * @version $Id: PriceUtils.java, v 0.1 2016年7月19日 上午11:13:17 MOUBK Exp $
 */
public class PriceUtils {
    /**
     * 价格转换
     * @param price
     * @return
     */
    public static BigDecimal convertPrice(Long price) {
        if (null == price) {
            return null;
        }
        BigDecimal source = new BigDecimal(price);
        BigDecimal finalPrice = source.divide(new BigDecimal(100));
        return finalPrice;
    }

    /**
     * 价格转换
     * @param price
     * @return
     */
    public static Long convertPrice(BigDecimal price) {
        if (null == price) {
            return null;
        }
        BigDecimal finalPrice = price.multiply(new BigDecimal(100));
        return finalPrice.longValue();
    }

    /**
     * 价格转换
     * @param price
     * @return
     */
    public static Long convertPrice(final Double price) {
        if (null == price) {
            return null;
        }
        Double toFen = price * 100;
        Long finalPrice = toFen.longValue();
        return finalPrice;
    }

}
