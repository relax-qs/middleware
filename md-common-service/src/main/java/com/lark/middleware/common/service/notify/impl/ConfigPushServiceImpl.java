package com.lark.middleware.common.service.notify.impl;

import com.alibaba.fastjson.JSON;
import com.lark.middleware.common.service.notify.ConfigPushService;
import com.lark.middleware.util.ByteUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/***
 * 配置推送服务实现
 * 
 * @author liguogang
 * @version $Id: ConfigPushServiceImpl.java, v 0.1 2016年5月29日 下午8:38:41 liguogang Exp $
 */
@Service
public class ConfigPushServiceImpl implements ConfigPushService {
    private final Logger                              logger        = LogManager.getLogger(this.getClass());

    private final Map<String, ConfigInstance>         instanceCache = new ConcurrentHashMap<>();

    private final String                              namePre       = "set";

//    @Autowired
//    private RedisTemplate<Serializable, Serializable> redisTemplate;

    /***
     * 配置项
     * 
     * @param config
     */
    public <T> ConfigInstance register(T instance) {
        if (null == instance) {
            throw new IllegalArgumentException("config instance is null");
        }

        String fullName = instance.getClass().getName();
        ConfigInstance configInstance = instanceCache.get(fullName);
        if (null == configInstance) {
            synchronized (fullName) {
                configInstance = instanceCache.get(fullName);
                if (null == configInstance) {
                    Method methods[] = instance.getClass().getDeclaredMethods();
                    Map<String, Method> methodMap = new HashMap<>();
                    for (Method method : methods) {
                        String name = method.getName();
                        if (!name.startsWith(namePre) || name.length() == namePre.length()) {
                            continue;
                        }

                        name = name.substring(3, name.length());
                        String tmp = name.substring(0, 1);
                        tmp = tmp.toLowerCase();
                        name = tmp + name.substring(1, name.length());
                        methodMap.put(name, method);
                    }
                    if (CollectionUtils.isEmpty(methodMap)) {
                        logger.error("Can not find set method in configName=" + fullName);
                    } else {
                        configInstance = new ConfigInstance(instance, methodMap);
                        instanceCache.putIfAbsent(fullName, configInstance);
                        logger.info("Cache configInstance,configName={},methodMap={}", fullName, methodMap);
                    }
                }
            }
        }

        return configInstance;
    }

    /****
     * 重新加载 配置文件
     * 
     * @param instance
     */
    public <T> void load(T instance) {
        String name = instance.getClass().getName();
        ConfigInstance configInstance = this.register(instance);
        Map<String, Method> methodMap = configInstance.getMethodMap();

//        boolean isSuccess = redisTemplate.execute(new RedisCallback<Boolean>() {
//            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                byte key[] = name.getBytes();
//                Map<byte[], byte[]> keyValue = connection.hGetAll(key);
//                if (CollectionUtils.isEmpty(keyValue)) {
//                    logger.info("configName have not values");
//                    return false;
//                }
//                Set<byte[]> missKey = null;
//                for (Entry<byte[], byte[]> entry : keyValue.entrySet()) {
//                    int result = invoke(configInstance, methodMap, entry.getKey(), entry.getValue());
//                    if (-1 == result) {
//                        if (missKey == null) {
//                            missKey = new HashSet<>();
//                        }
//                        missKey.add(entry.getKey());
//                    }
//                }
//
//                if (missKey != null) {
//                    Object missKeyArray = missKey.toArray();
//                    connection.hDel(key, (byte[]) missKeyArray);
//                    logger.warn("delete configInstance field,class={},keySize={}", name, missKey.size());
//                }
//                return true;
//            }
//        });

//        logger.error("load config,{},configInstance={}", (isSuccess ? "Y" : "N"), name);
    }

    /***
     * 推送消息
     * 
     * @param instance
     * @param key
     * @param value
     */
    public void push(byte[] instance, byte[] key, byte value[]) {
        String instanceName = new String(instance);
        ConfigInstance configInstance = instanceCache.get(instanceName);
        if (null == configInstance) {
            logger.error("Can not find instance from cache,name=" + instanceName);
            return;
        }
//        redisTemplate.execute(new RedisCallback<Boolean>() {
//            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                // 更新内存成功再更新redis
//                int result = invoke(configInstance, configInstance.getMethodMap(), key, value);
//                if (1 == result) {
//                    Map<byte[], byte[]> keyValue = new HashMap<>(2);
//                    keyValue.put(key, value);
//                    connection.hMSet(instance, keyValue);
//                }
//
//                logger.info("push,Y,instance={},key={},value={}", instanceName, new String(key));
//                return true;
//            }
//        });
    }

    private int invoke(ConfigInstance configInstance, Map<String, Method> methodMap, byte[] key, byte[] value) {
        String field = new String(key);
        Object instance = configInstance.getInstance();
        String name = instance.getClass().getName();
        Method method = methodMap.get(field);
        if (null == method) {
            return -1;
        }
        Parameter parameters[] = method.getParameters();
        if (parameters.length == 0) {
            try {
                method.invoke(configInstance.getInstance(), new Object[] { null });
            } catch (Exception e) {
                logger.error("invoke method error,methodName=" + method.getName() + ",class=" + name, e);
                return 0;
            }
        }

        Parameter parameter = parameters[0];
        Class<?> paramClass = parameter.getClass();

        try {
            Object object = this.deserialize(parameter, value);
            if (null == object) {
                logger.error("Can not deserialize,instanceClass={},paramClass={},value={}", instance.getClass(), paramClass, new String(value));
                return 0;
            } else {
                logger.info("deseriablize,field={},value={}", field, object);
            }
            method.invoke(configInstance.getInstance(), object);
        } catch (Exception e) {
            logger.error(
                "invoke method error,methodName=" + method.getName() + ",class=" + name + ",parameter=" + new String(value) + ",parameterClass=" + parameter,
                e);
            return 0;
        }

        return 1;
    }

    private Object deserialize(Parameter parameter, byte value[]) {
        Class<?> paramClass = parameter.getClass();
        if (paramClass.isPrimitive()) {
            if (paramClass == Boolean.class) {
                return ByteUtil.toBoolean(value);
            } else if (paramClass == Byte.class) {
                return value[0];
            } else if (paramClass == Short.class) {
                return ByteUtil.toShort(value);
            } else if (paramClass == Integer.class) {
                return ByteUtil.toInt(value);
            } else if (paramClass == Long.class) {
                return ByteUtil.toLong(value);
            }
        } else {
            return JSON.parseObject(value, paramClass);
        }

        return null;
    }

    private class ConfigInstance {
        // 配置对象实例
        private final Object              instance;
        // 配置对象的方法缓存,key=fieldName,value=method
        private final Map<String, Method> methodMap;

        private ConfigInstance(Object instance, Map<String, Method> methodMap) {
            this.instance = instance;
            this.methodMap = methodMap;
        }

        public Object getInstance() {
            return instance;
        }

        public Map<String, Method> getMethodMap() {
            return methodMap;
        }
    }

}
