package com.lark.middleware.model.convertor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by houenxun on 16/4/27.
 */
public class DataConvertorFactory {
    static Logger logger = LoggerFactory.getLogger(DataConvertorFactory.class);
    private static Map<Class, Map<Class, IDataConvertor>> convertorMap;

    public static <S,T>  IDataConvertor<S, T> getConvertor(Class<S> sourceClass, Class<T> targetClass){
        if(null !=convertorMap){
            Map<Class, IDataConvertor> temp = convertorMap.get(sourceClass);
            if(null != temp){
                return temp.get(targetClass);
            }
        }
        return null;
    }

    /**
     * 注册convertor
     * @param convertor
     * @param <S>
     * @param <T>
     */
    public static <S,T>  void  register(IDataConvertor<S, T> convertor){
        if(null == convertorMap){
            convertorMap = new HashMap<Class, Map<Class, IDataConvertor>>();
        }
        Map<Class, IDataConvertor> temp = convertorMap.get(convertor.getSourceClass());
        if(null == temp){
            temp = new HashMap<>();
            convertorMap.put(convertor.getSourceClass(), temp);
        }else{
            if(temp.containsKey(convertor.getTargetClass())){
                logger.error("重复注册: " +convertor.getClass());
            }
        }

        temp.put(convertor.getTargetClass(), convertor);

    }
}
