package com.lark.middleware.web.framework.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by houenxun on 16/7/28.
 */
public class WebUtil {
    public static final String REDIRECT_PREFIX        = "redirect:";  // 外部重定向前缀

    public static final String REDIRECT_FORWARD       = "forward:";   //  内部重定向前缀

    public static final String REDIRECT_URL_KEY       = "redirectUrl"; //重定向跳转链接

    public static final String DEFAULT_CHARSET        = "UTF-8";       // 编码格式


    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    /**
     * set Cookie
     * @param response
     * @param name
     * @param value
     */
    public static void setCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name.trim(), value.trim());
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
    }

    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    public static String getRealIP(HttpServletRequest request) {
        if (request.getHeader("X-Real-IP") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("X-Real-IP");
    }


    /***
     * 响应json 或 jsonp
     *
     * @param request
     * @param response
     * @param data
     * @throws IOException
     */
    public static void responseJson(HttpServletRequest request, HttpServletResponse response, Object data) throws IOException {
        response.setCharacterEncoding(DEFAULT_CHARSET);
        String jsonStr = toJson(data);
        PrintWriter writer = response.getWriter();
        writer.print(jsonStr);
        writer.flush();
        writer.close();
    }


    /***
     * 转化为json
     *
     * @param data
     * @return
     */
    public static String toJson(Object data) {
        if (null == data) {
            return "";
        }

        String result = null;

        if (data instanceof String) {
            result = (String) data;
        } else {
            result = JSONObject.toJSONString(data);
        }

        return result;
    }


}
