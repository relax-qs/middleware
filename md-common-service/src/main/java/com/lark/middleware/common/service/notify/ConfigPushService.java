package com.lark.middleware.common.service.notify;

/***
 * 配置推送服务
 * 
 * @author liguogang
 * @version $Id: ConfigPush.java, v 0.1 2016年5月29日 下午8:37:57 liguogang Exp $
 */
public interface ConfigPushService {
    /***
     * 加载配置项
     * 
     * @param instance
     */
    <T> void load(T instance);

    /***
     * 配置文件更新推送
     * 
     * @param instance
     * @param key
     * @param value
     */
    void push(byte[] instance, byte[] key, byte value[]);
}
