package com.lark.middleware.model.param;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Created by houenxun on 16/7/12.
 */

/**
 * 工具类,暂时存储校验信息
 */
public final class CheckParamUtils {
    static private ThreadLocal<Set<ConstraintViolation<Object>>> localErrors = new ThreadLocal<>();

    /**
     * 返回当前错误信息
     * @return
     */
    public static Set<ConstraintViolation<Object>> getErrors() {
        return localErrors.get();
    }

    /**
     * 设置错误信息
     * @param errors
     */
    public static void setErrors(Set<ConstraintViolation<Object>> errors) {
        localErrors.set(errors);
    }

    /**
     * 移除错误信息
     */
    public static void remove() {
        localErrors.remove();
    }
}
