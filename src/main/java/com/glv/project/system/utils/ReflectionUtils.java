package com.glv.project.system.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Slf4j
@SuppressWarnings("unused")
public class ReflectionUtils extends org.springframework.util.ReflectionUtils {

    /**
     * 获取类型中的所有属性名称
     */
    public static List<String> getFieldNames(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        List<String> fieldNames = new ArrayList<>(fieldList.size());
        for (Field field : fieldList) {
            field.setAccessible(true);
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }

    /**
     * 获取对象字段
     */
    public static List<Field> getFields(Object obj) {
        return getFields(obj.getClass());
    }

    /**
     * 获取类字段
     */
    public static List<Field> getFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    /**
     * 获取对象实例中的所有属性名称
     */
    public static List<String> getFieldNames(Object obj) {
        Class<?> clazz = obj.getClass();
        return getFieldNames(clazz);
    }

    /**
     * 根据名称获取对象属性的值
     */
    public static Object getFieldValue(Object target, String name) {
        try {
            List<Field> fields = getFields(target);
            for (Field field : fields) {
                if (StringUtils.equalsIgnoreCase(name, field.getName())) {
                    field.setAccessible(true);
                    return field.get(target);
                }
            }
        } catch (IllegalAccessException e) {
            log.error("反射获取属性值报错", e);
        }
        return null;
    }

    /**
     * 获取对象属性的值
     */
    public static Object getFieldValue(Object target, Field field) {
        try {
            field.setAccessible(true);
            return field.get(target);
        } catch (IllegalAccessException e) {
            log.error("反射获取属性值报错", e);
        }
        return null;
    }

    /**
     * 根据名称设置对象属性值
     */
    public static void setFieldValue(Object target, String name, Object value) {
        try {
            List<Field> fields = getFields(target);
            for (Field field : fields) {
                if (StringUtils.equalsIgnoreCase(name, field.getName())) {
                    field.setAccessible(true);
                    field.set(target, value);
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            log.error("反射设置属性值报错", e);
        }
    }

    /**
     * 设置字段的值
     */
    public static void setFieldValue(Object target, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(target, value);
        } catch (IllegalAccessException e) {
            log.error("反射设置属性值报错", e);
        }
    }
}
