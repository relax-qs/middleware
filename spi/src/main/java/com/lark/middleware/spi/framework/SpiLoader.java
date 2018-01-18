package com.lark.middleware.spi.framework;

import java.util.List;
import java.util.Map;

/***
 * 
 * 
 * @author liguogang
 * @version $Id: SpiLoader.java, v 0.1 2016年5月19日 下午2:12:18 liguogang Exp $
 */
public interface SpiLoader {
    /***
     * spi 加载器，通过这个加载器，加载到spi的实现
     * 
     * @param spiNodeInf
     * @return
     */
    public <T extends SpiNode> List<T> loadSpi(Class<T> spiNodeInf);

    /***
     * 转换为名称 与 value对应的spi节点
     * 
     * @param spiNodeInf
     * @return
     */
    public <T extends SpiNode> Map<String, T> mapSpi(Class<T> spiNodeInf);
}
