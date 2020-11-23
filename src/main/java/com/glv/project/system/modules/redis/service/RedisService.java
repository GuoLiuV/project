package com.glv.project.system.modules.redis.service;

/**
 * @author ZHOUXIANG
 */
public interface RedisService {

    /**
     * 向redis中存值
     *
     * @param key   键
     * @param value 值
     */
    void set(String key, Object value);

    /**
     * 向redis中存值，并指定过期时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 超时时间，单位秒
     */
    void set(String key, Object value, long timeout);

    /**
     * 从redis中获取值
     *
     * @param key 键
     * @return 值
     */
    Object get(String key);

    /**
     * 从redis中获取值
     *
     * @param key 键
     * @param dv  默认值
     * @return 值
     */
    Object getOrDefault(String key, Object dv);

    /**
     * 根据key删除值
     *
     * @param key 键
     */
    void remove(String key);
}
