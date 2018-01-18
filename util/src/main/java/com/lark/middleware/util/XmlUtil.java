package com.lark.middleware.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Created by houenxun on 16/5/16.
 */
public class XmlUtil {
    /**
     * 将xml转换成bean
     * @param xml
     * @param T
     * @param <T>
     * @return
     */
    public static  <T>  T toBean(String xml, Class<T> T){
        XStream xStream = new XStream(new DomDriver("UTF-8", new NoNameCoder()));
        xStream.processAnnotations(T);
        return (T)xStream.fromXML(xml);

    }

    /**
     * 将java bean转换成xml
     * @param object
     * @return
     */
    public static  String toXml(Object object){
        XStream xStream = new XStream(new DomDriver("UTF-8", new NoNameCoder()));
        xStream.autodetectAnnotations(true);

        return xStream.toXML(object);
    }
}
