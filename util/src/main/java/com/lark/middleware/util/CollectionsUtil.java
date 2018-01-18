package com.lark.middleware.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by houenxun on 16/4/28.
 * <p>
 * list 公共类
 */
public class CollectionsUtil {
    /**
     * 默认整型解析器
     */
    public static final IParser<Integer> DEFAULT_INTEGER_PARSER = (str) -> Integer.parseInt(str);
    /**
     * 默认长整型解析器
     */
    public static final IParser<Long> DEFAULT_LONG_PARSER = (str) -> Long.parseLong(str);
    /**
     * 默认布尔型解析器
     */
    public static final IParser<Boolean> DEFAULT_BOOLEAN_PARSER = (str) -> Boolean.parseBoolean(str);
    /**
     * 默认字符串解析器
     */
    public static final IParser<String> DEFAULT_STRING_PARSER = (str) -> str;

    /**
     * 字符串解析解析器,将付出
     *
     * @param <T>
     */
    public interface IParser<T>  extends ITransfer<String, T>{
        /**
         * 将字符串解析成指定类型
         * @param str
         * @return
         */
        T parse(String str);

        default  T transfer(String s){
            return this.parse(s);
        }

    }

    /**
     * 转换器
     *
     * @param <S>
     * @param <T>
     */
    public interface ITransfer<S, T> {
        /**
         * 将S类型换换成T类型
         * @param s
         * @return
         */
        T transfer(S s);
    }

    /**
     * list 解析,自动过滤空
     *
     * @param source
     * @param parser
     * @param split
     * @param <T>
     * @return
     */
    public static <T> List<T> parseList(String source, IParser<T> parser, String split) {
        List<T> list = new ArrayList<>();
        if (StringUtils.isBlank(source)) {
            return list;
        }
        for (String str : source.split(split)) {
            if (StringUtils.isNotBlank(str)) {
            	try {
            		T t = parser.parse(str);
                    if (t != null) {
                        list.add(t);
                    }
				} catch (Exception e) {
					continue;
				}
            }
        }
        return list;
    }

    /**
     * map 解析自动过滤空
     * @param source
     * @param keyParser
     * @param valueParser
     * @param KsSplit
     * @param kvSplit
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> parseMap(String source, IParser<K> keyParser, IParser<V> valueParser, String KsSplit, String kvSplit) {
        Map<K, V> map = new HashMap<>();
        for (String str : CollectionsUtil.parseList(source, DEFAULT_STRING_PARSER, KsSplit)) {
            String[] temp = str.split(kvSplit);
            if (null != temp && temp.length >= 2) {
                K key = keyParser.parse(temp[0]);
                V value = valueParser.parse(temp[1]);
                if (null != key && null != value) {
                    map.put(key, value);
                }
            }
        }
        return map;
    }

    /**
     * 将列表按照一定的格式进行组装
     *
     * @param source
     * @param split
     * @param <T>
     * @return
     */
    public static <T> String assembly(Collection<T> source, String split) {
        if (CollectionUtils.isNotEmpty(source)) {
            StringBuilder builder = new StringBuilder();
            for (T t : source) {
                builder.append(t).append(split);
            }
            if (builder.length() > 0) {
                return builder.substring(0, builder.length() - split.length());
            }
        }
        return null;
    }

    /**
     * 数据转换处理
     *
     * @param source
     * @param transfer   转换器
     * @param filterNull 是否过滤空对象
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> transfer(Collection<S> source, ITransfer<S, T> transfer, boolean filterNull) {
        List<T> targetList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(source)) {
            for (S s : source) {
                T t = transfer.transfer(s);
                if (t != null || !filterNull) {
                    targetList.add(t);
                }
            }
        }
        return targetList;

    }

    /**
     * 数据转换 转换map key不变,只更改value
     * @param source
     * @param transfer
     * @param filterNull
     * @param <K>
     * @param <S>
     * @param <T>
     * @return
     */
    public static <K,S, T> Map<K,T> transfer(Map<K,S> source, ITransfer<S, T> transfer, boolean filterNull){
        Map<K,T> target = new HashMap<>();
        if(null == source){
            return target;
        }
        for(K key : source.keySet()){
            S s = source.get(key);
            if(null != s){
                T t = transfer.transfer(s);

                if(t != null || !filterNull){
                    target.put(key, t);
                }
            }
        }
        return  target;
    }

    /**
     * 将集合转换成map
     *
     * @param source
     * @param transfer
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> Map<T, S> mapList(Collection<S> source, ITransfer<S, T> transfer) {
        // 保证有序
        Map<T, S> map = new TreeMap<>();
        if (CollectionUtils.isNotEmpty(source)) {
            for (S s : source) {
                if (null != s) {
                    T t = transfer.transfer(s);
                    if(null != t) {
                        map.put(t, s);
                    }
                }
            }
        }
        return map;
    }

    /**
     * 统计
     * @param source
     * @param transfer
     * @param <S>
     * @return
     */
    public static <S> Long statistics(Collection<S> source, ITransfer<S, Long> transfer){
         Long result = 0L;
        if(CollectionUtils.isNotEmpty(source)){
            for(S s: source){
                if(null != s){
                    Long t = transfer.transfer(s);
                    if(null != t) {
                        result += t;
                    }
                }
            }
        }
        return result;
    }


}
