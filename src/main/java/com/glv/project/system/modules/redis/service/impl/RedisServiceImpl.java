package com.glv.project.system.modules.redis.service.impl;

import com.glv.project.system.modules.redis.service.RedisService;
import com.glv.project.system.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author ZHOUXIANG
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Object getOrDefault(String key, Object dv) {
        Object value = redisTemplate.opsForValue().get(key);
        if (ObjectUtils.isNull(value)) {
            value = dv;
        }
        return value;
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }
}
