/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.lark.middleware.model.dto;

import org.junit.Test;

import com.lark.middleware.model.enums.CommonAttachmentEnum;
import com.lark.middleware.model.unittest.UTConstant;

/**
 * 
 * @author liguogang
 * @version $Id: ResultTest.java, v 0.1 2016年3月19日 下午11:47:53 liguogang Exp $
 */
public class ResultTest {
    @Test
    public void attachment() {
        Response<String> result = new Response<String>();
        result.setData("result test");
        result.attachment(CommonAttachmentEnum.SYSETM_ERROR.getAttachment());
        System.out.println(UTConstant.PASS_INFO + result);

        Response<Integer> intResult = new Response<Integer>();

        intResult.copyAttachment(result);

        System.out.println(UTConstant.PASS_INFO + intResult);
    }

}
