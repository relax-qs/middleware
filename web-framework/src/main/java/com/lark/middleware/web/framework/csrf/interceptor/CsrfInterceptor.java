package com.lark.middleware.web.framework.csrf.interceptor;

import com.lark.middleware.web.framework.csrf.TokenHelper;
import com.lark.middleware.web.framework.csrf.annotation.AddToken;
import com.lark.middleware.web.framework.csrf.annotation.CheckToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by houenxun on 2017/3/28.
 * csrf 拦截器,用于自动获取和校验csrf token
 */
public class CsrfInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenHelper tokenHelper;


    /**
     * 如果存在 CheckToken 标记，并且请求参数不包含csrf token 活着token 不匹配 抛出异常
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /**
         * 获取csrftoken
         */
        AddToken addToken = ((HandlerMethod) handler).getMethod().getDeclaredAnnotation(AddToken.class);
        if (null != addToken) {
            tokenHelper.addToken(response, addToken.timeout());
        }
        /**
         * 检查csrftoken
         */
        CheckToken checkToken = ((HandlerMethod) handler).getMethod().getDeclaredAnnotation(CheckToken.class);
        if (null != checkToken) {
            tokenHelper.checkToken(request, checkToken);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
