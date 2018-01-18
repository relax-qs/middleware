package com.lark.middleware.model.query;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class RedisPageResult<X> extends PageResult<X> {

    /**  */
    private static final long serialVersionUID = 1L;

    /**
     * 获取redis分页keys
     * 适用于redis中存json，同时为key列表关联
     * @param keys
     * @return
     */
    public <T> List<T> getRedisPageKeys(List<T> keys) {
        int pageNo = super.getPageNo();
        int pageSize = super.getPageSize();
        if (CollectionUtils.isNotEmpty(keys)) {
            // 设置总大小
            super.setTotalCount(keys.size());
            // 总数量满足当前分页，返回子key
            if (keys.size() > pageNo * pageSize) {
                return keys.subList((pageNo - 1) * pageSize, pageNo * pageSize);
            } else if (keys.size() > (pageNo - 1) * pageSize && keys.size() <= pageNo * pageSize) {
                return keys.subList((pageNo - 1) * pageSize, keys.size());
            }
        }
        return null;
    }
}
