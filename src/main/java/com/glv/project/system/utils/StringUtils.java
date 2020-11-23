package com.glv.project.system.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author ZHOUXIANG
 */
@Slf4j
public class StringUtils extends org.apache.commons.lang.StringUtils {

    /**
     * 判断字符串是否为空，包含null和空字符串
     */
    public static boolean isEmpty(String str) {
        if (Objects.nonNull(str)) {
            return str.trim().length() == 0;
        }
        return true;
    }

    /**
     * 判断字符串列表是否全都不为空，不为Null且不为空字符串""
     */
    public static boolean isNonEmpty(String... str) {
        boolean isNonEmpty = true;
        for (String temp : str) {
            if (StringUtils.isEmpty(temp)) {
                isNonEmpty = false;
                break;
            }
        }
        return isNonEmpty;
    }

    /**
     * 去除首尾指定字符串
     */
    public static String trimString(String str, String ch) {
        if (StringUtils.isNonEmpty(str)) {
            String regexStart = "^[" + ch + "]*";
            String regexEnd = "[" + ch + "]*$";
            return str.replaceAll(regexStart, "").replaceAll(regexEnd, "");
        }
        return null;
    }

    /**
     * 去除字符串中所有的ch字符
     */
    public static String trimStringAll(String str, String ch) {
        if (StringUtils.isNonEmpty(str)) {
            String regexStart = "[" + ch + "]*";
            String regexEnd = "[" + ch + "]*";
            return str.replaceAll(regexStart, "").replaceAll(regexEnd, "");
        }
        return null;
    }

    /**
     * 将以逗号分隔的字符串转成Set
     */
    public static Set<String> commaDelimitedListToSet(String str) {
        return org.springframework.util.StringUtils.commaDelimitedListToSet(str);
    }

    /**
     * 将集合转成以逗号分隔的字符串
     */
    public static String collectionToCommaDelimitedString(Collection<?> coll) {
        return org.springframework.util.StringUtils.collectionToCommaDelimitedString(coll);
    }

    /**
     * 将集合转成以某个符号分隔的字符串
     */
    public static String collectionToDelimitedString(Collection<?> coll, String delim) {
        return org.springframework.util.StringUtils.collectionToDelimitedString(coll, delim);
    }

    /**
     * 路径连接
     */
    public static String applyRelativePath(String path, String relativePath) {
        return org.springframework.util.StringUtils.applyRelativePath(path, relativePath);
    }

    /**
     * 将数组转成以逗号分隔的字符串
     */
    public static String arrayToCommaDelimitedString(Object[] arr) {
        return org.springframework.util.StringUtils.arrayToCommaDelimitedString(arr);
    }

    /**
     * 获取文件名称
     */
    public static String getFilename(String path) {
        return org.springframework.util.StringUtils.getFilename(path);
    }

    /**
     * 获取文件扩展名
     */
    public static String getFilenameExtension(String path) {
        return org.springframework.util.StringUtils.getFilenameExtension(path);
    }

    /**
     * 将驼峰形式字符串转成下划线形式
     */
    public static String camelCase2UnderLine(String str) {
        String[] camelItems = splitByCharacterTypeCamelCase(str);
        return join(camelItems, "_").toUpperCase();
    }

    /**
     * 字符串格式化
     *
     * @param formatter 格式字符串
     * @param objects   对象数组
     * @return 格式化字符串
     */
    public static String format(String formatter, Object... objects) {
        if (ObjectUtils.notNull(objects) && objects.length > 0) {
            if (StringUtils.isNotBlank(formatter)) {
                for (int i = 0; i < objects.length; i++) {
                    String val = String.format("{{%d}}", i);
                    formatter = formatter.replace(val, String.valueOf(objects[i]));
                }
            }
        }
        return formatter;
    }

    /**
     * 删除字符串中所有空白字符
     *
     * @param str 字符串
     * @return 删除后的字符串
     */
    public static String removeAllWhitespace(String str) {
        if (ObjectUtils.isNull(str)) {
            return null;
        } else if (isBlank(str)) {
            return "";
        }
        return str.replaceAll("\\s+", "");
    }

    /**
     * 是否包含任一个字符串
     *
     * @param str1    原字符串
     * @param strings 包含的字符串
     * @return 是否包含
     */
    public static boolean containsAny(String str1, String... strings) {
        if (ObjectUtils.notNull(strings) && strings.length > 0) {
            for (String s : strings) {
                if (contains(str1, s)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取以逗分隔的字符串，拼接成 sql in 语句中的内容
     *
     * @param params 以逗号分隔的字符串
     * @return sql in 字符串
     */
    public static String getSqlInStringByArray(String params) {
        String[] arr = StringUtils.split(params, ",");
        List<String> list = new ArrayList<>();
        for (String s : arr) {
            list.add(String.format("'%s'", s));
        }
        return join(list, ",");
    }

}
