package com.lark.middleware.model.convertor;

/**
 * Created by houenxun on 16/4/27.
 * 数据转换门面类
 */
public class DataConvertorFacade {
    /**
     * 生成目标对象并转换
     * @param source
     * @param targetClass
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T convertor(S source, Class<T> targetClass) {
        IDataConvertor convertor = DataConvertorFactory.getConvertor(source.getClass(), targetClass);
        if (null == convertor) {
            throw new RuntimeException("获取convertor失败");
        }
        return (T) convertor.convertor(source);
    }

    /**
     * 使用多个参数进行转换
     * @param <T>
     */
    public static <T> T convertor(T target, Object ... sources){
         if(null != sources){
             for(Object obj : sources){
                 target = convertor(obj, target);
             }

         }
        return target;
    }

    /**
     * 转换成目标对象
     * @param source
     * @param target
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T convertor(S source, T target) {
        IDataConvertor convertor = DataConvertorFactory.getConvertor(source.getClass(), target.getClass());
        if (null == convertor) {
            Class ss = target.getClass().getSuperclass();
            while(null != ss){
                convertor = DataConvertorFactory.getConvertor(source.getClass(), ss);
                if(null != convertor){
                    break;
                }
                ss = ss.getSuperclass();
            }
            if(null == convertor){
                throw new RuntimeException("获取convertor失败");
            }
        }
        return (T) convertor.convertor(source, target);
    }
}
