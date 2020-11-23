package com.glv.project.system.utils;

import java.util.Collection;

/**
 * @author ZHOUXIANG
 */
public class CollectionUtils extends org.apache.commons.collections.CollectionUtils {

    /**
     * 将集合转成数组
     */
    public static <T> T[] toArray(Collection<T> collection, T[] array) {
        return collection.toArray(array);
    }

    /**
     * 判断数组中是否包含某个元素
     */
    public static <T> boolean contains(T[] array, T ele) {
        for (T t : array) {
            if (t instanceof String) {
                if (ele.equals(t)) {
                    return true;
                }
            } else {
                if (ele == t) {
                    return true;
                }
            }
        }
        return false;
    }

}
