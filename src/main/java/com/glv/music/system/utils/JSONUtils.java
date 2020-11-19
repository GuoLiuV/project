package com.glv.music.system.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glv.music.system.modules.web.component.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZHOUXIANG
 */
@Slf4j
public class JSONUtils {

    private static ObjectMapper mapper;

    /**
     * <p>将Java对象序列化成JSON字符串</p>
     */
    public static String obj2Json(Object object) {
        try{
            return getMapper().writeValueAsString(object);
        }catch (Exception e) {
            log.error("对象转成JSON时错误", e);
        }
        return null;
    }

    /**
     * <p>将Java对象序列化JSON字符串，可以自定义包含属性，黙认配置在application.yml文件中，
     * 因为这里的ObjectMapper与Spring框架共用，因些在设置完属性后，要还原成原来的包含属性。</p>
     * <p>不推荐使用此方法，因为每次都会创建新的objectMapper，会有一定的性能问题。</p>
     * <p>因为原对象在使用一次后会缓存序列化属性，导致第二次设置的属性无效</p>
     */
    public static String obj2Json(Object object, JsonInclude.Include include) {
        ObjectMapper objectMapper = new ObjectMapper();
        BeanUtils.copyProperties(getMapper(), objectMapper);
        try{
            objectMapper.setSerializationInclusion(include);
            return objectMapper.writeValueAsString(object);
        }catch (Exception e) {
            log.error("对象转成JSON时错误", e);
        }
        return null;
    }

    /**
     * <p>将json字符串反序列化成Java对象</p>
     */
    public static <T> T json2Obj(String json, Class<T> type) {
        try{
            return getMapper().readValue(json, type);
        }catch (Exception e) {
            log.error("JSON转成对象时错误", e);
        }
        return null;
    }

    /**
     * <p>将json字符串反序列化成Java对象，
     * 涉及到数组泛型类型的转化，如果调用上述方法出现LinkedHashMap转换报错，
     * 可以使用这个方法</p>
     */
    public static <T> T json2Obj(String json, TypeReference<T> valueTypeRef) {
        try{
            return getMapper().readValue(json, valueTypeRef);
        }catch (Exception e) {
            log.error("JSON转成对象时错误", e);
        }
        return null;
    }

    /**
     * 获取Jackson ObjectMapper对象
     */
    private static ObjectMapper getMapper() {
        if (ObjectUtils.notNull(mapper)) {
            return mapper;
        }
        // 复用Spring的ObjectMapper以获更高的性能
        mapper = SpringContextHolder
                .getBean("jacksonObjectMapper",ObjectMapper.class);
        return mapper;
    }
}
