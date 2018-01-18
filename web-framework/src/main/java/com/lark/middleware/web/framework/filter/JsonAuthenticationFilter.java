package com.lark.middleware.web.framework.filter;


import com.lark.middleware.model.dto.Response;
import com.lark.middleware.model.enums.CommonAttachmentEnum;
import com.lark.middleware.web.framework.util.WebUtil;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by houenxun on 16/7/28.
 * 页面未登录拦截器
 */
public class JsonAuthenticationFilter extends FormAuthenticationFilter {
    /**
     * boeing 共享etb的登录页面, 拼接登录页面和当前页面到etb
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        //saveRequest(request);
        redirectToLogin(request, response);
    }
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        String loginUrl = getLoginUrl();
        Response response1 = new Response();
        response1.attachment(CommonAttachmentEnum.PERMISSION_DENIED.getAttachment(), "没有登录");
        Map<String, Object> map = new HashMap<>();
        map.put(WebUtil.REDIRECT_URL_KEY, loginUrl);
        response1.setData(map);
        WebUtil.responseJson((HttpServletRequest) request, (HttpServletResponse) response, response1);
    }

}
