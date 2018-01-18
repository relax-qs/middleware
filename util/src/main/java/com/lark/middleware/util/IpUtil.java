package com.lark.middleware.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by houenxun on 16/5/10.
 */
public class IpUtil {
    /**
     * 返回本机器ip地址
     * @return
     */
    public static String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("unknown host!");
        }
        return null;
    }

    /**
     * 获取下单用户请求Ip
     * @param request
     * @return
     */
    public static String getRealIP(HttpServletRequest request) {
        if (request.getHeader("X-Real-IP") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("X-Real-IP");
    }

}
