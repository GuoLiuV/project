package com.glv.project.system.modules.cache.config;

import com.glv.project.system.modules.cache.StriveCacheManager;
import com.glv.project.system.modules.cache.exception.StriveCacheException;
import com.glv.project.system.utils.CollectionUtils;
import com.glv.project.system.utils.ObjectUtils;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.lang.Nullable;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>框架自定义缓存配置，整合了ehcache和redis，支持一级或二级缓存
 *
 * @author ZHOUXIANG
 */
@Configuration
@EnableCaching
@EnableConfigurationProperties(CacheProperties.class)
public class StriveCacheConfig {

    @Resource
    private CacheProperties cacheProperties;

    /**
     * 配置自定义CacheManager，Spring框架将使用此CacheManager进行操作缓存。
     */
    @Bean("cacheManager")
    @Primary
    @ConditionalOnProperty(prefix = "strive.cache", name = "enabled", havingValue = "true")
    public CacheManager striveCacheManager(@Nullable EhCacheCacheManager ehCacheCacheManager,
                                                                     @Nullable RedisCacheManager redisCacheManager) {
        //构建StriveCacheManager
        StriveCacheManager striveCacheManager = new StriveCacheManager();
        List<String> cacheNames = cacheProperties.getCacheNames();
        if (CollectionUtils.isEmpty(cacheNames)) {
            throw new StriveCacheException("未配置缓存名称");
        }
        /*
         * 依次向StriveCacheManager中加入ehCacheCacheManager和redisCacheManager，
         * 加入的顺序决定缓存的级别
         */
        if (ObjectUtils.notNull(ehCacheCacheManager)) {
            net.sf.ehcache.CacheManager cacheManager = ehCacheCacheManager.getCacheManager();
            if (ObjectUtils.isNull(cacheManager)) {
                throw new StriveCacheException("ehcache CacheManager为空");
            }
            cacheNames.forEach(cacheManager::addCacheIfAbsent);
            striveCacheManager.getCacheManagers().add(ehCacheCacheManager);
        }
        if (ObjectUtils.notNull(redisCacheManager)) {
            cacheNames.forEach(redisCacheManager::getCache);
            striveCacheManager.getCacheManagers().add(redisCacheManager);
        }
        if (CollectionUtils.isEmpty(striveCacheManager.getCacheManagers())) {
            throw new StriveCacheException("启用缓存后，至少需要开启ehcache或redis其中一种缓存");
        }
        return striveCacheManager;
    }

}
