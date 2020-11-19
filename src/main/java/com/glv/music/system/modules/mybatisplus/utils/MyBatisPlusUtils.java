package com.glv.music.system.modules.mybatisplus.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.request.enums.SortOrderEnum;
import com.glv.music.system.utils.CollectionUtils;
import com.glv.music.system.utils.ObjectUtils;
import com.glv.music.system.utils.ReflectionUtils;
import com.glv.music.system.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author ZHOUXIANG
 */
@Slf4j
@SuppressWarnings("unused")
public class MyBatisPlusUtils {

    /**
     * 构建查询与排序条件，properties用户指定eq查询,其它的是like查询
     */
    public static <T, R> void buildQuery(QueryWrapper<T> queryWrapper,
                                         PageRequest<R> pageRequest, String... properties) {
        buildQuery(queryWrapper, pageRequest.getCondition(), properties);
        buildSortOrderQuery(queryWrapper, pageRequest.getSort());
    }

    /**
     * 构建或查询
     */
    public static <T, R> void buildOrQuery(QueryWrapper<T> queryWrapper,
                                         PageRequest<R> pageRequest, String... properties) {
        buildOrQuery(queryWrapper, pageRequest.getCondition(), properties);
        buildSortOrderQuery(queryWrapper, pageRequest.getSort());
    }

    /**
     * 构建条件查询
     */
    public static <T> void buildQuery(QueryWrapper<T> queryWrapper, Object condition, String... properties) {
        if (ObjectUtils.isNull(condition)) {
            return;
        }
        List<Field> fields = ReflectionUtils.getFields(condition);
        for (Field field : fields) {
            Object value = ReflectionUtils.getFieldValue(condition, field);
            if (ObjectUtils.notNull(value)) {
                if (field.getType() == String.class) {
                    String val = String.valueOf(value);
                    if (CollectionUtils.contains(properties, field.getName())) {
                        queryWrapper.eq(StringUtils.isNotBlank(val),
                                StringUtils.camelCase2UnderLine(field.getName()), value);
                    } else {
                        queryWrapper.like(StringUtils.isNotBlank(val),
                                StringUtils.camelCase2UnderLine(field.getName()), value);
                    }
                } else {
                    queryWrapper.eq(StringUtils.camelCase2UnderLine(field.getName()), value);
                }
            }
        }
    }

    public static <T> void buildOrQuery(QueryWrapper<T> queryWrapper, Object condition, String... properties) {
        if (ObjectUtils.isNull(condition)) {
            return;
        }
        List<Field> fields = ReflectionUtils.getFields(condition);
        for (Field field : fields) {
            Object value = ReflectionUtils.getFieldValue(condition, field);
            if (ObjectUtils.notNull(value)) {
                if (field.getType() == String.class) {
                    String val = String.valueOf(value);
                    if (CollectionUtils.contains(properties, field.getName())) {
                        queryWrapper.or().eq(StringUtils.isNotBlank(val),
                                StringUtils.camelCase2UnderLine(field.getName()), value);
                    } else {
                        queryWrapper.or().like(StringUtils.isNotBlank(val),
                                StringUtils.camelCase2UnderLine(field.getName()), value);
                    }
                } else {
                    queryWrapper.or().eq(StringUtils.camelCase2UnderLine(field.getName()), value);
                }
            }
        }
        if (StringUtils.isBlank(queryWrapper.getCustomSqlSegment())) {
            queryWrapper.apply("1 = 1");
        }
    }

    /**
     * 构建排序
     */
    public static <T, R> void buildSortOrderQuery(QueryWrapper<T> queryWrapper, PageRequest<R> pageRequest) {
        buildSortOrderQuery(queryWrapper, pageRequest.getSort());
    }

    /**
     * 解析排序字段并排序
     */
    public static <T> void buildSortOrderQuery(QueryWrapper<T> queryWrapper, String sort) {
        if (StringUtils.isNotBlank(sort)) {
            Map<String, List<String>> orderProperties = getSortProperties(sort);
            if (ObjectUtils.notNull(orderProperties)) {
                List<String> ascProperties = orderProperties.get("asc");
                List<String> descProperties = orderProperties.get("desc");
                queryWrapper.orderBy(
                        CollectionUtils.isNotEmpty(ascProperties), true,
                        CollectionUtils.toArray(ascProperties, new String[0]));
                queryWrapper.orderBy(
                        CollectionUtils.isNotEmpty(descProperties), false,
                        CollectionUtils.toArray(descProperties, new String[0]));
            }
        }
    }

    /**
     * 解析排序字段
     */
    private static Map<String, List<String>> getSortProperties(String sort) {
        if (StringUtils.isNotBlank(sort)) {
            try {
                Map<String, List<String>> sortProperties = new HashMap<>(2);
                List<String> ascProperties = new ArrayList<>();
                List<String> descProperties = new ArrayList<>();
                Arrays.stream(StringUtils.split(sort, ","))
                        .forEach(sortOrderStr -> {
                            String[] sortOrderArray = StringUtils.split(sortOrderStr, ":");
                            if (StringUtils.equalsIgnoreCase(SortOrderEnum.ASC.name(), sortOrderArray[1])) {
                                ascProperties.add(StringUtils.camelCase2UnderLine(sortOrderArray[0]));
                            }
                            if (StringUtils.equalsIgnoreCase(SortOrderEnum.DESC.name(), sortOrderArray[1])) {
                                descProperties.add(StringUtils.camelCase2UnderLine(sortOrderArray[0]));
                            }
                        });
                sortProperties.put("asc", ascProperties);
                sortProperties.put("desc", descProperties);
                return sortProperties;
            } catch (Exception e) {
                log.error("排序字符串格式不正确");
            }
        }
        return null;
    }
}
