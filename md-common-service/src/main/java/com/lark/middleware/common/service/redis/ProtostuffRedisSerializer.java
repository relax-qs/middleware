package com.lark.middleware.common.service.redis;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class ProtostuffRedisSerializer<T> implements RedisSerializer<T> {

    private static Schema objectWrapperSchema = RuntimeSchema.createFrom(ObjectWrapper.class);

    @Override
    public byte[] serialize(T t) throws SerializationException {
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            ObjectWrapper<T> objectWrapper = new ObjectWrapper<>();
            objectWrapper.setValue(t);
            byte[] bytes = ProtostuffIOUtil.toByteArray(objectWrapper, objectWrapperSchema, buffer);
            return bytes;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if(ArrayUtils.isEmpty(bytes)) {
            return null;
        }
        try {
            ObjectWrapper<T> objectWrapper = new ObjectWrapper<>();
            ProtostuffIOUtil.mergeFrom(bytes, objectWrapper, objectWrapperSchema);
            return objectWrapper.getValue();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static class ObjectWrapper<T> {
        private T value;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}