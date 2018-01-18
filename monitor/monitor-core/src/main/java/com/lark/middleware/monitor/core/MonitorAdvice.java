package com.lark.middleware.monitor.core;

import com.lark.middleware.monitor.common.MonitorEvent;
import com.lark.middleware.monitor.common.MonitorNode;
import com.lark.middleware.monitor.common.MonitorPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Created by houenxun on 2017/3/27.
 */
public class MonitorAdvice {

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

        Method methodImpl = pjp.getTarget().getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
        MonitorNode annotation = methodImpl.getDeclaredAnnotation(MonitorNode.class);

        MonitorPoint monitorPoint = new MonitorPoint();
        monitorPoint.setCode(annotation.code());
        monitorPoint.setName(monitorPoint.getName());
        MonitorEvent monitorEvent = new MonitorEvent();

        monitorEvent.setPoint(monitorPoint);
        monitorEvent.setStartTime(System.currentTimeMillis());
        try {
            MonitorFacade.enter(method.getName());
            return pjp.proceed();
        } finally {
            MonitorFacade.delivery(monitorEvent);
        }
    }


}
