package com.glv.project.system.modules.cache.config;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ehcache缓存相关的配置
 *
 * @author ZHOUXIANG
 */
@Configuration
public class EhcacheConfig {

    /**
     * 配置并创建EhCacheManagerFactoryBean实例
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(CacheProperties cacheProperties) {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(cacheProperties.getEhcache().getConfig());
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean;
    }

    /**
     * 创建EhCacheCacheManager实例
     */
    @Bean
    @ConditionalOnProperty(prefix = "strive.cache.ehcache", name = "enabled", havingValue = "true")
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean) {
        net.sf.ehcache.CacheManager cacheManager = ehCacheManagerFactoryBean.getObject();
        assert null != cacheManager : "net.sf.ehcache.CacheManager为空";
        return new EhCacheCacheManager(cacheManager);
    }
}
