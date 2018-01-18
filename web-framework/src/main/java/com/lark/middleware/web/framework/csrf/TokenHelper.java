package com.lark.middleware.web.framework.csrf;

import com.lark.middleware.model.BusinessException;
import com.lark.middleware.redis.client.api.IRedisClient;
import com.lark.middleware.web.framework.csrf.annotation.CheckToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by houenxun on 2017/3/28.
 */
public class TokenHelper {

    @Autowired
    private IRedisClient redisClient;


    public static final String TOKEN_REDIS_KEY = "TOKEN_";
    public static final String CSRF_TOKEN_KEY = "csrf-token";

    private String noTokenCode;
    private String noTokenMessage;
    private String tokenExpireCode;
    private String tokenExpireMessage;

    private Long timeout = 30 * 60L;

    /**
     * 给response 添加token
     *
     * @param response
     * @param tm       指定超时时间 如果传入空 使用默认超时时间
     */
    public void addToken(HttpServletResponse response, Long tm) {
        long theTm = (tm == null || tm <= 0) ? timeout : tm;

        String token = UUID.randomUUID().toString();
        redisClient.valueOps().set(TOKEN_REDIS_KEY + token, token, theTm, TimeUnit.SECONDS);
        response.addHeader(CSRF_TOKEN_KEY, token);
    }

    /**
     * 校验token
     *
     * @param request
     * @param checkToken
     */
    public void checkToken(HttpServletRequest request, CheckToken checkToken) {
        String token = request.getHeader(CSRF_TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(StringUtils.isBlank(checkToken.noTokenCode()) ? noTokenCode : checkToken.noTokenCode(), StringUtils.isBlank(checkToken.noTokenMessage()) ? noTokenMessage : checkToken.noTokenMessage());
        }

        String tt = redisClient.slience().valueOps().get(TOKEN_REDIS_KEY + token);
        redisClient.slience().delete(TOKEN_REDIS_KEY + token);
        if (StringUtils.isBlank(tt)) {
            throw new BusinessException(StringUtils.isBlank(checkToken.tokenExpireCode()) ? tokenExpireCode : checkToken.tokenExpireCode(), StringUtils.isBlank(checkToken.tokenExpireMessage()) ? tokenExpireMessage : checkToken.tokenExpireMessage());
        }

    }

    public String getNoTokenCode() {
        return noTokenCode;
    }

    public void setNoTokenCode(String noTokenCode) {
        this.noTokenCode = noTokenCode;
    }

    public String getNoTokenMessage() {
        return noTokenMessage;
    }

    public void setNoTokenMessage(String noTokenMessage) {
        this.noTokenMessage = noTokenMessage;
    }

    public String getTokenExpireCode() {
        return tokenExpireCode;
    }

    public void setTokenExpireCode(String tokenExpireCode) {
        this.tokenExpireCode = tokenExpireCode;
    }

    public String getTokenExpireMessage() {
        return tokenExpireMessage;
    }

    public void setTokenExpireMessage(String tokenExpireMessage) {
        this.tokenExpireMessage = tokenExpireMessage;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }
}
