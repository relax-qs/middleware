package com.lark.middleware.common.service.redis;

import org.springframework.data.redis.core.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by jianyu.liu@hnlark.com on 2016/6/18.
 *
 * @author jianyu.liu@hnlark.com
 */
public class SlientRedisTemplate<K, V> extends RedisTemplate<K, V> {

    public ClusterOperations<K, V> opsForCluster() {
        return createSilentProxy(super.opsForCluster(), ClusterOperations.class);
    }

    public <HK, HV> HashOperations<K, HK, HV> opsForHashSilent() {
        return createSilentProxy(super.opsForHash(), HashOperations.class);
    }

    public HyperLogLogOperations<K, V> opsForHyperLogLogSilent() {
        return createSilentProxy(super.opsForHyperLogLog(), HyperLogLogOperations.class);
    }

    public ListOperations opsForListSilent() {
        return createSilentProxy(super.opsForList(), ListOperations.class);
    }

    public SetOperations opsForSetSilent() {
        return createSilentProxy(super.opsForSet(), SetOperations.class);
    }

    public ValueOperations opsForValueSilent() {
        return createSilentProxy(super.opsForValue(), ValueOperations.class);
    }

    public ZSetOperations opsForZSetSilent() {
        return createSilentProxy(super.opsForZSet(), ZSetOperations.class);
    }

    private <T> T createSilentProxy(T target, Class<T> interfaceClass) {
        T proxy = (T) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InterfaceWrapperHandler(target)
        );

        return proxy;

    }

    class InterfaceWrapperHandler implements InvocationHandler {

        private final Object target;

        private InterfaceWrapperHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                return method.invoke(target, args);
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage(), e);
            } catch (InvocationTargetException e) {
                logger.error("调用Redis时出现错误，将会返回null.错误如下：", e.getTargetException());
            }
            return null;
        }
    }
}
