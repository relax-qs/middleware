package com.lark.middleware.web.framework.util;

/**
 * Created by houenxun on 16/7/29.
 */

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 资源工具类
 */
public class AssetsUtil implements Map<String, Object>{
    private static Map<String, Object> assetsMap ;

    public AssetsUtil(){
        System.out.println("");
    }
    public AssetsUtil(Map<String, Object> map){
        assetsMap = map;
    }

    @Override
    public int size() {
        return assetsMap.size();
    }

    @Override
    public boolean isEmpty() {
        return assetsMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return assetsMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return assetsMap.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return assetsMap.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return assetsMap.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return assetsMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        assetsMap.putAll(m);
    }

    @Override
    public void clear() {
        assetsMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return assetsMap.keySet();
    }

    @Override
    public Collection<Object> values() {
        return assetsMap.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return assetsMap.entrySet();
    }
}
