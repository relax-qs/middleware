package com.lark.middleware.web.framework.authz.aop;

import com.lark.middleware.web.framework.domain.UserSubject;
import com.lark.middleware.web.framework.util.UserSubjectUtil;
import org.apache.shiro.aop.AnnotationMethodInterceptor;
import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;
import org.apache.shiro.authz.aop.UserAnnotationHandler;

import java.lang.annotation.Annotation;

/**
 * Created by houenxun on 2016/12/14.
 */
public class UserAnnotationMethodInterceptor extends AuthorizingAnnotationMethodInterceptor {
    /**
     * Default no-argument constructor that ensures this interceptor looks for
     * <p>
     * {@link org.apache.shiro.authz.annotation.RequiresUser RequiresUser} annotations in a method
     * declaration.
     */
    public UserAnnotationMethodInterceptor() {
        super(new UserHandler());
    }

    /**
     * @param resolver
     * @since 1.1
     */
    public UserAnnotationMethodInterceptor(AnnotationResolver resolver) {
        super(new UserHandler(), resolver);
    }

}

class UserHandler extends UserAnnotationHandler {
    @Override
    public void assertAuthorized(Annotation a) throws AuthorizationException {
        UserSubject subject = UserSubjectUtil.getCurrentUser();
        if (a instanceof RequiresUser && subject == null) {
            throw new UnauthenticatedException("Attempting to perform a user-only operation.  The current Subject is " +
                    "not a user (they haven't been authenticated or remembered from a previous login).  " +
                    "Access denied.");
        }
    }
}

