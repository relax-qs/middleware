package com.lark.middleware.web.framework.filter;

import com.lark.middleware.web.framework.util.WebUtil;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by houenxun on 16/7/28.
 * 页面未登录拦截器
 */
public class PageAuthenticationFilter extends FormAuthenticationFilter {
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
        // 获取当前页面的的绝对url
        String url = ((HttpServletRequest) request).getRequestURL().toString();
        url = URLEncoder.encode(url, WebUtil.DEFAULT_CHARSET);
        ((HttpServletResponse) response).sendRedirect(loginUrl + "?" + WebUtil.REDIRECT_URL_KEY + "=" + url);

    }
}



