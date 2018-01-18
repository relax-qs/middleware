package com.lark.middleware.util;

/**
 * hnlark.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */


import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author MOUBK
 * @version $Id: HttpClientUtils.java, v 0.1 2016年7月13日 下午9:50:46 MOUBK Exp $
 */
public class HttpClientUtils {

    /**
     * httpclient post请求
     * @param url
     * @param map
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String post(String url, Map<String, String> map, String fakeIp) throws ClientProtocolException, IOException {
        String responseContent = null;
        HttpClient client = new DefaultHttpClient();
        //设置超时时间
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        HttpPost httpPost = new HttpPost(url); // 创建HttpPost
        List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // 构建POST请求的表单参数
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        //修改ip为了调取oms接口
        if (StringUtils.isNotBlank(fakeIp)) {
            httpPost.addHeader("x-forwarded-for", fakeIp);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
        HttpResponse response = client.execute(httpPost); // 执行POST请求
        HttpEntity entity = response.getEntity(); // 获取响应实体
        if (null != entity) {
            responseContent = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity); // Consume response content
        }
        client.getConnectionManager().shutdown();
        return responseContent;
    }
}
