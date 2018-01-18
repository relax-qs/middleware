package com.lark.middleware.web.framework.authz.aop;

import com.lark.middleware.web.framework.domain.UserSubject;
import com.lark.middleware.web.framework.util.UserSubjectUtil;
import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;
import org.apache.shiro.authz.aop.GuestAnnotationHandler;

import java.lang.annotation.Annotation;

/**
 * Created by houenxun on 2016/12/12.
 */
public class GuestAnnotationMethodInterceptor extends AuthorizingAnnotationMethodInterceptor {

    /**
     * Default no-argument constructor that ensures this interceptor looks for
     * {@link org.apache.shiro.authz.annotation.RequiresGuest RequiresGuest} annotations in a method
     * declaration.
     */
    public GuestAnnotationMethodInterceptor() {
        super(new GuestHandler());
    }

    /**
     * @param resolver
     * @since 1.1
     */
    public GuestAnnotationMethodInterceptor(AnnotationResolver resolver) {
        super(new GuestHandler(), resolver);
    }

}

class GuestHandler extends GuestAnnotationHandler {
    @Override
    public void assertAuthorized(Annotation a) throws AuthorizationException {
        UserSubject userSubject = UserSubjectUtil.getCurrentUser();
        if (a instanceof RequiresGuest && userSubject != null) {
            throw new UnauthenticatedException("Attempting to perform a guest-only operation.  The current Subject is " +
                    "not a guest (they have been authenticated or remembered from a previous login).  Access " +
                    "denied.");
        }
    }
}
