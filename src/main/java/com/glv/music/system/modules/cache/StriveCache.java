package com.glv.music.system.modules.cache;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 框架自定义的缓存Cache，整合Ehcache和RedisCache，实例一级或二级缓存功能
 * @author ZHOUXIANG
 */
@Data
@NoArgsConstructor
@Slf4j
public class StriveCache implements Cache {

    /**
     * 缓存名称
     */
    private String name;

    /**
     * 最多可以添加两个缓存, List中保存的ehcache和redisCache缓存对象的名称都是一样的，
     * 都是根据相同的名称创建出来的。
     */
    private final List<Cache> caches = new ArrayList<>(2);

    StriveCache(String name) {
        this.name = name;
    }

    @Override
    @NonNull
    public String getName() {
        return this.name;
    }

    @Override
    @NonNull
    public Object getNativeCache() {
        return this;
    }

    /**
     * 首先获取第一级缓存的值，如果找到直接返回，
     * 如果在第一级缓存中没有找到相关的值，就判断有没有二级缓存
     * 如果有二级缓存则从二级缓存中获取相关的值，如果没有返回Null，
     * 如果有，则将二级存缓的值存到一级缓存中。
     */
    @Override
    public ValueWrapper get(@NonNull Object key) {
        ValueWrapper valueWrapper = caches.get(0).get(key);
        if (null == valueWrapper && caches.size() > 1) {
            valueWrapper = caches.get(1).get(key);
            if (null != valueWrapper) {
                caches.get(0).put(key, valueWrapper.get());
                log.debug("根据键{}获取到的二级缓存的值{}，并将二级缓存值存入一级缓存", key, valueWrapper.get());
            }
        }
        log.debug("根据键{}获取到的缓存值{}", key, valueWrapper == null ? "NULL" : valueWrapper.get());
        return valueWrapper;
    }

    /**
     * <p>根据值对象类型和缓存键查询缓存值并返回对应类型的值</p>
     */
    @Override
    public <T> T get(@NonNull Object key, Class<T> type) {
        T value = caches.get(0).get(key, type);
        if (null == value && caches.size() > 1) {
            value = caches.get(1).get(key, type);
            if (null != value) {
                caches.get(0).put(key, value);
                log.debug("根据键{}获取到的二级缓存的值{}，并将二级缓存值存入一级缓存", key, value);
            }
        }
        log.debug("根据键{}获取到的缓存值为{}", key, value == null ? "NULL" : value);
        return value;
    }

    /**
     * <p>通过缓存Key查找缓存的值，如果不存在就调用valueLoader回调函数，返回一个值，
     * 并将此值作为缓存值返回，否则返回Null</p>
     */
    @Override
    @SuppressWarnings("unchecked")
    synchronized public <T> T get(@NonNull Object key, @NonNull Callable<T> valueLoader) {
        ValueWrapper valueWrapper = get(key);
        if (null != valueWrapper) {
            log.debug("根据键{}获取到的缓存值{}", key, valueWrapper.get());
            return (T) valueWrapper.get();
        }
        try {
            T t = valueLoader.call();
            log.debug("根据键{}获取到的缓存值{}", key, t);
            return t;
        } catch (Exception e) {
            log.error("获取缓存键{}的值时，回调函数异常：{}", key, e.getMessage());
        }
        return null;
    }

    /**
     * 设置缓存
     *
     * @param key   缓存键
     * @param value 缓存值
     */
    @Override
    public void put(@NonNull Object key, Object value) {
        caches.get(0).put(key, value);
        if (caches.size() > 1) {
            caches.get(1).put(key, value);
            log.debug("成功存入二级缓存键为{}的数据为{}", key, value);
        }
        log.debug("成功缓存键为{}的数据{}", key, value);
    }

    @Override
    public ValueWrapper putIfAbsent(@NonNull Object key, Object value) {
        ValueWrapper valueWrapper = get(key);
        if (null == valueWrapper) {
            put(key, value);
        }
        log.debug("根据键{}获取到的缓存值{}", key, valueWrapper == null ? "NULL" : valueWrapper.get());
        return valueWrapper;
    }

    /**
     * 将键对应的缓存值清除掉
     *
     * @param key 要清除缓存数据的键
     */
    @Override
    public void evict(@NonNull Object key) {
        caches.get(0).evict(key);
        if (caches.size() > 1) {
            caches.get(1).evict(key);
            log.debug("清除二级缓存中键为 {} 的缓存值", key);
        }
        log.debug("清除键为 {} 的缓存值", key);
    }

    /**
     * 清除所有缓存
     */
    @Override
    public void clear() {
        caches.get(0).clear();
        log.debug("请除一级缓存内容");
        if (caches.size() > 1) {
            caches.get(1).clear();
            log.debug("请除二级缓存内容");
        }
        log.debug("请除所有缓存内容");
    }

}
