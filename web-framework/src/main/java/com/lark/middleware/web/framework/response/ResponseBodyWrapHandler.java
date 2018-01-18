package com.lark.middleware.web.framework.response;

import com.lark.middleware.model.dto.Response;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * Created by houenxun on 16/7/10.
 * 代理@ResonseBody返回结果,
 * 判断外层是否有response 如果没有增加response,默认是成功
 */
public class ResponseBodyWrapHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    public ResponseBodyWrapHandler(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
         if(!(returnValue instanceof Response)){
             returnValue = new Response(returnValue);
         }
        delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }
}
