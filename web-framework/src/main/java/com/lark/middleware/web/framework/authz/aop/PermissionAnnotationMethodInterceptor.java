package com.lark.middleware.web.framework.authz.aop;

import com.lark.middleware.web.framework.domain.UserSubject;
import com.lark.middleware.web.framework.util.UserSubjectUtil;
import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;
import org.apache.shiro.authz.aop.PermissionAnnotationHandler;

import java.lang.annotation.Annotation;

/**
 * Created by houenxun on 2016/12/12.
 */
public class PermissionAnnotationMethodInterceptor extends AuthorizingAnnotationMethodInterceptor {


    /**
     * Default no-argument constructor that ensures this interceptor looks for
     * {@link org.apache.shiro.authz.annotation.RequiresPermissions RequiresPermissions} annotations in a method declaration.
     */
    public PermissionAnnotationMethodInterceptor() {
        super(new PermissionHandler());
    }

    /**
     * @param resolver
     * @since 1.1
     */
    public PermissionAnnotationMethodInterceptor(AnnotationResolver resolver) {
        super(new PermissionHandler() , resolver);
    }

}

class PermissionHandler extends PermissionAnnotationHandler {
    @Override
    public void assertAuthorized(Annotation a) throws AuthorizationException {
        if (!(a instanceof RequiresPermissions)) return;

        RequiresPermissions rpAnnotation = (RequiresPermissions) a;
        String[] perms = getAnnotationValue(a);
        UserSubject userSubject = UserSubjectUtil.getCurrentUser();

        if (null == userSubject) {
            throw new UnauthenticatedException("The current Subject is not authenticated.  Access denied.");
        }

        if (perms.length == 1) {
            if (userSubject.isPermitted(perms[0])) {
                throw new UnauthenticatedException("The current Subject is not authenticated.  Access denied.");
            }
            return;
        }
        if (Logical.AND.equals(rpAnnotation.logical())) {
            if (!userSubject.isPermittedAll(perms)) {
                throw new UnauthenticatedException("The current Subject is not authenticated.  Access denied.");
            }
        }
        if (Logical.OR.equals(rpAnnotation.logical())) {
            if (!userSubject.isPermittedAny(perms)) {
                throw new UnauthenticatedException("The current Subject is not authenticated.  Access denied.");
            }
        }
    }
}
