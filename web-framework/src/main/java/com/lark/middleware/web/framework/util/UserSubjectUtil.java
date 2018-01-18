package com.lark.middleware.web.framework.util;

import com.lark.middleware.web.framework.domain.UserSubject;

/**
 * Created by houenxun on 2016/12/12.
 */
public class UserSubjectUtil {
    private static ThreadLocal<UserSubject> users = new ThreadLocal<>();
    /**
     * @return
     * @see
     */
    public static UserSubject getCurrentUser() {
        return users.get();
    }

    /**
     * 设置当前登录用户
     *
     * @param user
     */
    public static void setCurrentUser(UserSubject user) {
        users.set(user);
    }

    /**
     * 清除当前登录用户
     */
    public static void remove() {
        users.remove();
    }
}
