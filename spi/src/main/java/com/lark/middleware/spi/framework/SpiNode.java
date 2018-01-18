package com.lark.middleware.spi.framework;

/***
 * 定义一个spi的节点
 * 
 * @author liguogang
 * @version $Id: SpiPoint.java, v 0.1 2016年5月19日 下午12:28:15 liguogang Exp $
 */
public interface SpiNode {
    /***
     * 节点的执行顺序
     * 
     * @return
     */
    int sequence();

    /***
     * 节点的名称
     * 
     * @return
     */
    String name();
}
