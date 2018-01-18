package com.lark.middleware.model.param;


import com.lark.middleware.model.BusinessException;
import com.lark.middleware.model.enums.CommonAttachmentEnum;
import com.lark.middleware.util.ValidatorUtil;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by houenxun on 16/7/11.
 */
public class CheckParamAdvice {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 检查参数是否符合bean validation规范,如果不符合,进一步看根据CheckParam注解直接抛出错误异常还是,将错误信息暂存到CheckParamUtils中
     *
     * @param pjp 连接点
     */
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        // 调用方法的参数
        Object[] args = pjp.getArgs();

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        Set<ConstraintViolation<Object>> errors = ValidatorUtil.validate(pjp.getTarget(), method, args);
        if (CollectionUtils.isNotEmpty(errors)) {
            logger.error("param check error: errors={}", errors);
            Method methodImpl = pjp.getTarget().getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
            CheckParam annotation = methodImpl.getDeclaredAnnotation(CheckParam.class);
            if (null == annotation) {
                annotation = method.getDeclaredAnnotation(CheckParam.class);
            }
            // 没有注解 默认返回
            if (null == annotation) {
                return pjp.proceed();
            }
            if (annotation.autoRespone()) {
                throw new BusinessException(CommonAttachmentEnum.PARAM_ERROR.getAttachment()).addParam("errors", errors);
            } else {

                try {
                    CheckParamUtils.setErrors(errors);
                    return pjp.proceed();
                } finally {
                    CheckParamUtils.remove();
                }
            }
        } else {
            return pjp.proceed();
        }
    }


}
