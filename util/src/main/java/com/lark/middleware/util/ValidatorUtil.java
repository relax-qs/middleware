package com.lark.middleware.util;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;


/**
 * Created by houenxun on 16/5/17.
 * bean validate 验证工具类
 */
public class ValidatorUtil {

    static Validator validator;
    static ExecutableValidator executableValidator;

    static {
        ValidatorFactory vf  = Validation.buildDefaultValidatorFactory();

        validator = vf.getValidator();
        executableValidator = validator.forExecutables();
    }

    /**
     * 验证对象
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> Set<ConstraintViolation<T>> validate(T object) {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        return validator.validate(object);
    }

    /**
     * 校验函数参数
     *
     * @param object
     * @param method
     * @param parameterValues
     * @param <T>
     * @return
     */
    public static <T> Set<ConstraintViolation<T>> validate(T object, Method method, Object[] parameterValues) {
        /*ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        ExecutableValidator executableValidator = validator.forExecutables();*/
        return executableValidator.validateParameters(object, method, parameterValues);
    }


}
