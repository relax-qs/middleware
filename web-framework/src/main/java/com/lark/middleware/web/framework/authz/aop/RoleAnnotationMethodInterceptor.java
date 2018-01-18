package com.lark.middleware.web.framework.authz.aop;

import com.lark.middleware.web.framework.domain.UserSubject;
import com.lark.middleware.web.framework.util.UserSubjectUtil;
import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;
import org.apache.shiro.authz.aop.RoleAnnotationHandler;

import java.lang.annotation.Annotation;

/**
 * Created by houenxun on 2016/12/12.
 */
public class RoleAnnotationMethodInterceptor extends AuthorizingAnnotationMethodInterceptor {

    /**
     * Default no-argument constructor that ensures this interceptor looks for
     * {@link RequiresRoles RequiresRoles} annotations in a method declaration.
     */
    public RoleAnnotationMethodInterceptor() {
        super(new RoleHandler());
    }

    /**
     * @param resolver
     * @since 1.1
     */
    public RoleAnnotationMethodInterceptor(AnnotationResolver resolver) {
        super(new RoleHandler(), resolver);
    }
}

class RoleHandler extends RoleAnnotationHandler {
    @Override
    public void assertAuthorized(Annotation a) throws AuthorizationException {
        if (!(a instanceof RequiresRoles)) return;

        RequiresRoles rrAnnotation = (RequiresRoles) a;
        String[] roles = rrAnnotation.value();

        UserSubject subject = UserSubjectUtil.getCurrentUser();
        if (null == subject) {
            throw new UnauthenticatedException("The current Subject is not authenticated.  Access denied.");

        }

        if (roles.length == 1) {
            if (!subject.hasRole(roles[0])) {
                throw new UnauthenticatedException("The current Subject is not authenticated.  Access denied.");
            }
            return;
        }
        if (Logical.AND.equals(rrAnnotation.logical())) {
            if (!subject.hasAllRoles(roles)) {
                throw new UnauthenticatedException("The current Subject does not has all roles .  Access denied.");
            }
            return;
        }
        if (Logical.OR.equals(rrAnnotation.logical())) {
            if (!subject.hasAnyRole(roles)) {
                throw new UnauthenticatedException("The current Subject does not has all roles .  Access denied.");
            }
            return;
        }
    }

}
