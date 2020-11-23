package com.glv.project.system.utils;

import com.glv.project.system.modules.exception.StriveException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.*;

/**
 * @author ZHOUXIANG
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * 获取对象中属性为NULL的属性名称
     */
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 复制实例对象中的不为NULL的属性
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target, String... ignoreProperties) {
        String[] ip = getNullPropertyNames(src);
        Set<String> igSet = new HashSet<>();
        if (ip.length > 0) {
            igSet.addAll(Arrays.asList(ip));
        }
        if (ObjectUtils.notNull(ignoreProperties) && ignoreProperties.length > 0) {
            igSet.addAll(Arrays.asList(ignoreProperties));
        }
        String[] result = new String[igSet.size()];
        copyProperties(src, target, igSet.toArray(result));
    }

    /**
     * 根据目标对象属性数量复制实例对象中不为null的属性
     */
    public static void copyPropertiesByTargetIgnoreNull(Object src, Object target, String... ignoreProperties) {
        List<String> targetNames = ReflectionUtils.getFieldNames(target);
        for (String name : targetNames) {
            if (CollectionUtils.contains(ignoreProperties, name)) {
                continue;
            }
            Object srcValue = ReflectionUtils.getFieldValue(src, name);
            if (ObjectUtils.notNull(srcValue)) {
                ReflectionUtils.setFieldValue(target, name, srcValue);
            }
        }
    }

    /**
     * 将bean转换成map
     *
     * @param obj bean对象
     * @return map对象
     */
    public static Map<String, Object> bean2Map(Object obj) {
        if (ObjectUtils.isNull(obj)) {
            throw new StriveException("要转换成Map的对象为空");
        }
        List<String> names = ReflectionUtils.getFieldNames(obj);
        if (CollectionUtils.isNotEmpty(names)) {
            Map<String, Object> map = new HashMap<>(names.size());
            names.forEach(name -> map.put(name, ReflectionUtils.getFieldValue(obj, name)));
            return map;
        }
        return Collections.emptyMap();
    }

}
