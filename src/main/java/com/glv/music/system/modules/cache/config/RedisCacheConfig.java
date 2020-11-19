package com.glv.music.system.modules.cache.config;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis缓存相关配置，只有redis和redis缓存都开启了，才会启用redis缓存
 *
 * @author ZHOUXIANG
 */
@Configuration
public class RedisCacheConfig {

    /**
     * 配置RedisCacheManager
     *
     * @param redisConnectionFactory RedisConnectionFactory实例
     * @return RedisCacheManager实例
     */
    @Bean
    @ConditionalOnProperty(prefix = "strive.cache.redis", name = "enabled", havingValue = "true")
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory,
                                               RedisSerializer<Object> jsonRedisSerializer,
                                               CacheProperties cacheProperties) {

        // 构建 RedisCacheManager
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory);

        // 重新定义Redis键值的序列化方式，Key键是按照字符串形式序列化的，Value值是将对象序列化成Json字符串
        RedisSerializationContext.SerializationPair<String> keySerializationPair = RedisSerializationContext
                .SerializationPair.fromSerializer(new StringRedisSerializer());
        RedisSerializationContext.SerializationPair<Object> valueSerializationPair =
                RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer);

        // 设置缓存配置
        RedisCacheConfiguration defaultRedisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig().serializeKeysWith(keySerializationPair)
                .serializeValuesWith(valueSerializationPair)
                .entryTtl(cacheProperties.getRedis().getTimeToLive());

        if (cacheProperties.getRedis().isUseKeyPrefix()) {
            defaultRedisCacheConfiguration.prefixKeysWith(
                    cacheProperties.getRedis().getKeyPrefix());
        } else {
            defaultRedisCacheConfiguration.disableKeyPrefix();
        }

        if (!cacheProperties.getRedis().isCacheNullValues()) {
            defaultRedisCacheConfiguration.disableCachingNullValues();
        }

        builder.cacheDefaults(defaultRedisCacheConfiguration);

        return builder.build();
    }

}
