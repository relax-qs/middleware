package com.lark.middleware.web.framework.authz.aop;

import com.lark.middleware.web.framework.domain.UserSubject;
import com.lark.middleware.web.framework.util.UserSubjectUtil;
import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.aop.AuthenticatedAnnotationHandler;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;

import java.lang.annotation.Annotation;

/**
 * Created by houenxun on 2016/12/12.
 * 是否授权拦截器
 */
public class AuthenticatedAnnotationMethodInterceptor extends AuthorizingAnnotationMethodInterceptor {
    /**
     * Default no-argument constructor that ensures this interceptor looks for
     * {@link org.apache.shiro.authz.annotation.RequiresAuthentication RequiresAuthentication} annotations in a method
     * declaration.
     */
    public AuthenticatedAnnotationMethodInterceptor() {
        super(new AuthenticatedHandler());
    }

    /**
     * @param resolver
     * @since 1.1
     */
    public AuthenticatedAnnotationMethodInterceptor(AnnotationResolver resolver) {
        super(new AuthenticatedHandler(), resolver);
    }
}

class AuthenticatedHandler extends AuthenticatedAnnotationHandler {
    @Override
    public void assertAuthorized(Annotation a) throws UnauthenticatedException {
        UserSubject userSubject = UserSubjectUtil.getCurrentUser();
        if (a instanceof RequiresAuthentication && null == userSubject) {
            throw new UnauthenticatedException("The current Subject is not authenticated.  Access denied.");
        }
    }
}
