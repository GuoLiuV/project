package com.glv.music.system.modules.redis.handler;

/**
 * @author ZHOUXIANG
 */
public interface RedisKeyExpirationHandler {

    /**
     * 处理函数
     *
     * @param keyName 键名
     */
    void handle(String keyName);
}
