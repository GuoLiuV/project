package com.glv.project.system.modules.redis.utils;

import com.glv.project.system.modules.redis.service.RedisService;
import com.glv.project.system.modules.web.component.SpringContextHolder;

/**
 * @author ZHOUXIANG
 */
public class RedisUtils {

    /**
     * redis 存储
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {
        RedisService redisService = SpringContextHolder.getBean(RedisService.class);
        redisService.set(key, value);
    }

    /**
     * redis 存储并指定过期时间
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     */
    public static void set(String key, Object value, long timeout) {
        RedisService redisService = SpringContextHolder.getBean(RedisService.class);
        redisService.set(key, value, timeout);
    }

    /**
     * redis 获取值
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        RedisService redisService = SpringContextHolder.getBean(RedisService.class);
        return redisService.get(key);
    }

    /**
     * 删除键值
     * @param key 键
     */
    public static void remove(String key) {
        RedisService redisService = SpringContextHolder.getBean(RedisService.class);
        redisService.remove(key);
    }
}
