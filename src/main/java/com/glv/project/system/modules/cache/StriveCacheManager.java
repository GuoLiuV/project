package com.glv.project.system.modules.cache;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.NonNull;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 自定义多缓存接口
 * @author ZHOUXIANG
 */
@Data
@Slf4j
public class StriveCacheManager implements CacheManager {

    @Resource
    private CacheProperties cacheProperties;

    private final List<CacheManager> cacheManagers = new ArrayList<>(2);

    private final Map<String, StriveCache> striveCacheMap = new ConcurrentHashMap<>(16);

    private volatile Set<String> cacheNames = Collections.emptySet();

    /**
     * <p>首先从striveCache缓存的Map对象中根据缓存名称获取缓存对象，如果存在直接返回。
     * 如果不存在，则由ehcache或redisCache构造一个StriveCache自定义缓存对象。</p>
     */
    @Override
    public Cache getCache(@NonNull String name) {
        StriveCache striveCache = striveCacheMap.get(name);
        if (null != striveCache) {
            return striveCache;
        } else {
            synchronized (this.striveCacheMap){
                /*
                 * 在新创建一个StriveCache缓存之前，先判断在此线程阻塞的过程，有没有其它线程已经创建了该缓存，
                 * 如果创建了就直接返回，否则创建一个新的缓存。
                 */
                striveCache = striveCacheMap.get(name);
                if (null == striveCache){
                    log.debug("创建一个新的缓存，名称为：{}", name);
                    /*
                     * 根据缓存名称获取ehcache和redisCache的缓存，如果缓存不存在 则cacheManager.getCache(name)方法中的
                     * getMissingCache方法会根据黙认的配置创建一个新的缓存，语句中的.filter(Objects::nonNull)只是起到防守的作用，
                     * 可以不加。
                     */
                    List<Cache> caches = cacheManagers.stream()
                            .map(cacheManager -> cacheManager.getCache(name))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    // 根据缓存的名称创建一个缓存
                    striveCache = new StriveCache(name);
                    striveCache.getCaches().addAll(caches);
                    // 将创建的缓存放入Map中
                    striveCacheMap.put(name, striveCache);
                    // 更新缓存名称集合
                    this.updateCacheNames(name);
                    log.info("系统中使用的缓存数量：{}", caches.size());
                }
            }
        }
        return striveCache;
    }

    @Override
    @NonNull
    public Collection<String> getCacheNames() {
        /*
         * 从Spring配置中的获取缓存名猜测
         */
        Set<String> cacheNames = new HashSet<>(cacheProperties.getCacheNames());
        this.cacheNames = Collections.unmodifiableSet(cacheNames);
        log.info("缓存的名称为：{}", cacheNames);
        return cacheNames;
    }

    /**
     * 更新缓存名称集合，这样写法是为了节省内存空间。
     * @param name 缓存名称
     */
    private void updateCacheNames(String name) {
        Set<String> cacheNames = new LinkedHashSet<>(this.cacheNames.size() + 1);
        cacheNames.addAll(this.cacheNames);
        cacheNames.add(name);
        this.cacheNames = Collections.unmodifiableSet(cacheNames);
    }

}
