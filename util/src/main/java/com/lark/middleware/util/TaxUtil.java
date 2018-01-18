package com.lark.middleware.util;

import java.math.BigDecimal;

/**
 * 
 * @author MOUBK
 * @version $Id: TaxUtil.java, v 0.1 2016年7月29日 上午10:32:18 MOUBK Exp $
 */
public class TaxUtil {
    private static final Integer maxScale     = 5;
    private static final Integer defalutScale = 2;

    /**
     * 商品价格倒推，默认精度2
     * 只支持物流价格为0的货品
     * price = total/(1+valueAddedTaxRate*discountRate+((discountRate*consumptionTaxRate*(1+valueAddedTaxRate))/(1-consumptionTaxRate)))
     * @param total 总价
     * @param valueAddedTaxRate 增值税比率 0.17
     * @param consumptionTaxRate 消费税比率 0.3
     * @param discountRate 优惠比率 0.7
     * @return 小数点后保留两位
     */
    public static BigDecimal retrodictGoodPrice(BigDecimal total, BigDecimal valueAddedTaxRate, BigDecimal consumptionTaxRate, BigDecimal discountRate) {
        return retrodictGoodPrice(total, valueAddedTaxRate, consumptionTaxRate, discountRate, defalutScale);
    }

    /**
     * 商品价格倒推，精度范围1-5
     * 只支持物流价格为0的货品
     * price = total/(1+valueAddedTaxRate*discountRate+((discountRate*consumptionTaxRate*(1+valueAddedTaxRate))/(1-consumptionTaxRate)))
     * @param total 总价
     * @param valueAddedTaxRate 增值税比率 0.17
     * @param consumptionTaxRate 消费税比率 0.3
     * @param discountRate 优惠比率 0.7
     * @param scale 精度
     * @return
     */
    public static BigDecimal retrodictGoodPrice(BigDecimal total, BigDecimal valueAddedTaxRate, BigDecimal consumptionTaxRate, BigDecimal discountRate,
                                                Integer scale) {
        if (null == total || null == valueAddedTaxRate || null == consumptionTaxRate || null == discountRate) {
            return null;
        }
        scale = checkScale(scale);
        BigDecimal step1 = discountRate.multiply(consumptionTaxRate).multiply(valueAddedTaxRate.add(new BigDecimal(1)));
        BigDecimal step2 = new BigDecimal(1).subtract(consumptionTaxRate);
        BigDecimal step3 = step1.divide(step2, maxScale, BigDecimal.ROUND_HALF_UP);
        BigDecimal step4 = valueAddedTaxRate.multiply(discountRate);
        BigDecimal step5 = new BigDecimal(1).add(step4).add(step3);
        BigDecimal step6 = total.divide(step5, scale, BigDecimal.ROUND_HALF_UP);
        return step6;
    }

    /**
     * 计算消费税
     * @param goodPrice
     * @param taxRate
     * @return
     */
    public static BigDecimal genConsumptionTax(BigDecimal goodPrice, BigDecimal consumptionTaxRate, BigDecimal discountRate) {
        return genConsumptionTax(goodPrice, consumptionTaxRate, discountRate, defalutScale);
    }

    /**
     * 计算消费税
     * @param goodPrice
     * @param taxRate
     * @return
     */
    public static BigDecimal genConsumptionTax(BigDecimal goodPrice, BigDecimal consumptionTaxRate, BigDecimal discountRate, Integer scale) {
        if (null == goodPrice || null == consumptionTaxRate || null == discountRate) {
            return null;
        }
        scale = checkScale(scale);
        BigDecimal step1 = new BigDecimal(1).subtract(consumptionTaxRate);
        BigDecimal step2 = goodPrice.multiply(consumptionTaxRate).divide(step1, maxScale, BigDecimal.ROUND_HALF_UP);
        BigDecimal step3 = step2.multiply(discountRate);
        step3 = step3.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return step3;
    }

    /**
     * 计算增值税，默认精度2
     * @param goodPrice
     * @param consumptionTaxRate
     * @param valueAddedTaxRate
     * @return
     */
    public static BigDecimal genValueAddedTax(BigDecimal goodPrice, BigDecimal consumptionTaxRate, BigDecimal valueAddedTaxRate, BigDecimal discountRate) {
        return genValueAddedTax(goodPrice, consumptionTaxRate, valueAddedTaxRate, discountRate, defalutScale);
    }

    /**
     * 计算增值税，默认精度范围1-5
     * @param goodPrice 价格
     * @param consumptionTaxRate 消费税率
     * @param valueAddedTaxRate 增值税率
     * @param discountRate 折扣率
     * @param scale 精度
     * @return
     */
    public static BigDecimal genValueAddedTax(BigDecimal goodPrice, BigDecimal consumptionTaxRate, BigDecimal valueAddedTaxRate, BigDecimal discountRate,
                                              Integer scale) {
        if (null == goodPrice || null == consumptionTaxRate || null == valueAddedTaxRate || null == discountRate) {
            return null;
        }
        scale = checkScale(scale);
        // 获取未打折的增值税
        BigDecimal consumptionTax = genConsumptionTax(goodPrice, consumptionTaxRate, new BigDecimal(1));
        BigDecimal step2 = goodPrice.add(consumptionTax).multiply(valueAddedTaxRate);
        BigDecimal step3 = step2.multiply(discountRate);
        step3 = step3.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return step3;
    }

    /**
     * 计算综合税，默认精度为2
     * @param goodPrice
     * @param consumptionTaxRate
     * @param valueAddedTaxRate
     * @param discountRate
     * @return
     */
    public static BigDecimal genCompositeTax(BigDecimal goodPrice, BigDecimal consumptionTaxRate, BigDecimal valueAddedTaxRate, BigDecimal discountRate) {
        return genCompositeTax(goodPrice, consumptionTaxRate, valueAddedTaxRate, discountRate, defalutScale);
    }

    /**
     * 计算综合税，精度范围1-5
     * @param goodPrice 价格
     * @param consumptionTaxRate 消费税率
     * @param valueAddedTaxRate 增值税率
     * @param discountRate 折扣率
     * @param scale 精确度
     * @return
     */
    public static BigDecimal genCompositeTax(BigDecimal goodPrice, BigDecimal consumptionTaxRate, BigDecimal valueAddedTaxRate, BigDecimal discountRate,
                                             Integer scale) {
        if (null == goodPrice || null == consumptionTaxRate || null == valueAddedTaxRate || null == discountRate) {
            return null;
        }
        scale = checkScale(scale);
        //折扣后的消费税
        BigDecimal consumptionTax = genConsumptionTax(goodPrice, consumptionTaxRate, discountRate, scale);
        //折扣后的增值税
        BigDecimal valueAddedTax = genValueAddedTax(goodPrice, consumptionTaxRate, valueAddedTaxRate, discountRate, scale);
        BigDecimal step2 = consumptionTax.add(valueAddedTax);
        return step2;
    }

    /**
     * 检查精度
     * @param scale
     * @return
     */
    private static Integer checkScale(Integer scale) {
        //精度不能大于2
        if (null == scale || scale <= 0 || scale > maxScale) {
            return defalutScale;
        }
        return scale;
    }
}
