package com.glv.project.system.utils;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;

/**
 * 此工具类是使用simple-xml库处理xml与对象之间的转换的，关于simple-xml的使用请参考：
 * http://simple.sourceforge.net/download/stream/doc/tutorial/tutorial.php
 *
 * @author ZHOUXIANG
 */
@Slf4j
public class XMLUtils {

    private static Serializer serializer;

    /**
     * <p>将对象序列化成xml字符串</p>
     */
    public static String obj2Xml(Object object) {
        try {
            StringWriter writer = new StringWriter();
            getSerializer().write(object, writer);
            return String.valueOf(writer);
        } catch (Exception e) {
            log.error("对象转xml错误", e);
        }
        return null;
    }

    /**
     * <p>将xml字符串反序列化成Java对象</p>
     */
    public static <T> T xml2Obj(String xml, Class<T> type) {
        try {
            return getSerializer().read(type, xml, false);
        } catch (Exception e) {
            log.error("xml转换对象错误", e);
        }
        return null;
    }

    /**
     * 获取Simple-XML序列化对象
     */
    private static Serializer getSerializer() {
        if (ObjectUtils.notNull(serializer)) {
            return serializer;
        }
        serializer = new Persister();
        return serializer;
    }
}
