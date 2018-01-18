package com.lark.middleware.web.framework.domain;

import java.util.Map;
import java.util.Set;

/**
 * Created by houenxun on 2016/12/12.
 * 登录用户主题
 */
public class UserSubject {

    /**
     * 用户id
     */
    protected Long userId;
    /**
     * 用户名称
     */
    protected String userName;

    /**
     * 登录名称
     */
    protected String loginName;
    /**
     * 登录方式id
     */
    protected Long loginId;

    /**
     * 角色
     */
    protected Set<String> roles;

    /**
     * 权限
     */
    protected Set<String> auths;

    Map<String, Object> extProperties;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getAuths() {
        return auths;
    }

    public void setAuths(Set<String> auths) {
        this.auths = auths;
    }

    public Map<String, Object> getExtProperties() {
        return extProperties;
    }

    public void setExtProperties(Map<String, Object> extProperties) {
        this.extProperties = extProperties;
    }


    public boolean isPermitted(String permission) {
        return null != auths && auths.contains(permission);
    }


    public boolean[] isPermitted(String... permissions) {
        boolean[] result = new boolean[permissions.length];
        int i = 0;
        for (String perm : permissions) {
            result[i++] = this.isPermitted(perm);
        }
        return result;
    }


    public boolean isPermittedAll(String... permissions) {
        for (String perm : permissions) {
            if (!this.isPermitted(perm)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPermittedAny(String... permissions) {
        for (String perm : permissions) {
            if (this.isPermitted(perm)) {
                return true;
            }
        }
        return false;
    }


    public boolean hasRole(String roleIdentifier) {
        return null != roles && roles.contains(roleIdentifier);
    }

    public boolean[] hasRoles(String... roles) {
        boolean[] result = new boolean[roles.length];
        int i = 0;
        for (String role : roles) {
            result[i++] = this.isPermitted(role);
        }
        return result;
    }

    public boolean hasAllRoles(String... roles) {
        for (String role : roles) {
            if (!this.isPermitted(role)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasAnyRole(String... roles) {
        for (String role : roles) {
            if (this.isPermitted(role)) {
                return true;
            }
        }
        return false;
    }

}
