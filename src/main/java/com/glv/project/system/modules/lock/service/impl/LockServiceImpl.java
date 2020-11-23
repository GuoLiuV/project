package com.glv.project.system.modules.lock.service.impl;

import com.glv.project.system.modules.lock.service.LockService;
import com.glv.project.system.utils.RandomStringUtils;
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
public class LockServiceImpl implements LockService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean lock(String key, long timeout) {
        String value = RandomStringUtils.genUUID();
        Boolean ok = redisTemplate.opsForValue()
                .setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
        return ok != null && ok;
    }

    @Override
    public void unlock(String key) {
        redisTemplate.delete(key);
    }
}
