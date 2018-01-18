package com.lark.middleware.common.service.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author destinyliu
 */
public abstract class RedisHelper {

    private static Logger logger = LoggerFactory.getLogger(RedisHelper.class);

    public static Long getLong(final RedisTemplate redisTemplate, String key) {

        final RedisTemplate<String, Long> longRedisTemplate = redisTemplate;
        try {
            Long value = longRedisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer keySerializer = longRedisTemplate.getKeySerializer();
                    byte[] keyBytes =keySerializer.serialize(key);
                    byte[] valueBytes = connection.get(keyBytes);
                    if (valueBytes == null) {
                        return null;
                    }
                    return Long.valueOf(new String(valueBytes));
                }
            });
            return value;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }

    }

    public static Double getDouble(final RedisTemplate redisTemplate, String key) {

        final RedisTemplate<String, Double> doubleRedisTemplate1 = redisTemplate;
        try {
            Double value = doubleRedisTemplate1.execute(new RedisCallback<Double>() {
                @Override
                public Double doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer keySerializer = doubleRedisTemplate1.getKeySerializer();
                    byte[] keyBytes =keySerializer.serialize(key);
                    byte[] valueBytes = connection.get(keyBytes);
                    if (valueBytes == null) {
                        return null;
                    }
                    return Double.valueOf(new String(valueBytes));
                }
            });
            return value;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }

    }

}
