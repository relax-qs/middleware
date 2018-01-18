package com.lark.middleware.spi.framework.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.lark.middleware.spi.framework.SpiLoader;
import com.lark.middleware.spi.framework.SpiNode;

/***
 * spi的容器
 * 
 * @author liguogang
 * @version $Id: SpiContainer.java, v 0.1 2016年5月19日 下午12:31:34 liguogang Exp $
 */
@Service
public class SpiLoaderImpl implements SpiLoader, ApplicationContextAware {
    private Logger             log               = LogManager.getLogger(this.getClass());

    private SpiNodeComparator  spiNodeComparator = new SpiNodeComparator();

    private ApplicationContext applicationContext;

    public <T extends SpiNode> List<T> loadSpi(Class<T> spiNodeInf) {
        Map<String, T> spiNodeBeans = this.applicationContext.getBeansOfType(spiNodeInf);
        if (CollectionUtils.isEmpty(spiNodeBeans)) {
            log.warn("can not found any spiNode,class={}", spiNodeInf);
            return null;
        }
        List<T> spiNodes = new ArrayList<T>(spiNodeBeans.values());
        // 排序
        Collections.sort(spiNodes, spiNodeComparator);

        for (SpiNode spiNode : spiNodes) {
            log.info("find spiNode class={},name={}", spiNode.getClass(), spiNode.name());
        }

        return spiNodes;
    }

    /***
     * 后去spi名称和bean的映射
     * 
     * @param spiNodeInf
     * @return
     */
    public <T extends SpiNode> Map<String, T> mapSpi(Class<T> spiNodeInf) {
        Map<String, T> spiNodeBeans = this.applicationContext.getBeansOfType(spiNodeInf);
        if (CollectionUtils.isEmpty(spiNodeBeans)) {
            log.warn("can not found any spiNode,class={}", spiNodeInf);
            return null;
        }

        Map<String, T> mapSpi = new HashMap<>();

        for (T spiNode : spiNodeBeans.values()) {
            mapSpi.put(spiNode.name(), spiNode);
            log.info("find spiNode class={},name={}", spiNode.getClass(), spiNode.name());
        }

        return mapSpi;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private class SpiNodeComparator implements Comparator<SpiNode> {
        public int compare(SpiNode node1, SpiNode node2) {
            return node1.sequence() - node2.sequence();
        }

        /***
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        public boolean equals(Object obj) {
            return false;
        }
    }
}
