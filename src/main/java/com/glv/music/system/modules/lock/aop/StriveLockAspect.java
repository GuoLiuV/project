package com.glv.music.system.modules.lock.aop;

import com.glv.music.system.modules.lock.annotation.StriveLock;
import com.glv.music.system.modules.lock.exception.StriveLockException;
import com.glv.music.system.modules.property.StriveProperties;
import com.glv.music.system.utils.ObjectUtils;
import com.glv.music.system.utils.RandomStringUtils;
import com.glv.music.system.utils.SpElUtils;
import com.glv.music.system.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>实现拦截使用了@RedisLock的方法，并尝试加锁，成功则执行该方法，
 * 失败则根据相应的策略执行阻塞或非阻塞逻辑</p>
 * <p>注意，要使此方法生效，必须开启redis，将strive.redis.enabled设置为true，
 * 以让redis配置生效</p>
 *
 * @author ZHOUXIANG
 */
@Slf4j
@Aspect
@Component
public class StriveLockAspect {

    private final RedisTemplate<String, String> redisTemplate;

    private final StriveProperties striveProperties;

    @Autowired
    public StriveLockAspect(RedisTemplate<String, String> redisTemplate,
                            StriveProperties striveProperties) {
        this.redisTemplate = redisTemplate;
        this.striveProperties = striveProperties;
    }

    /**
     * 加锁处理逻辑方法
     */
    @Around("@annotation(striveLock)")
    public Object handler(ProceedingJoinPoint proceedingJoinPoint,
                          StriveLock striveLock) throws Throwable {
        if (striveProperties.isLockEnabled()) {
            return this.lockHandler(proceedingJoinPoint, striveLock);
        }
        return this.lockLessHandler(proceedingJoinPoint);
    }

    /**
     * 开关未开启时直接执行
     */
    private Object lockLessHandler(
            ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return proceedingJoinPoint.proceed();
    }

    /**
     * 分布式锁开关开启时执行锁机制
     */
    private Object lockHandler(ProceedingJoinPoint proceedingJoinPoint,
                               StriveLock striveLock) throws Throwable {
        //构建Spring表达式参数
        Map<String, Object> variables = ObjectUtils.getAopMethodContext(proceedingJoinPoint);
        // 解析Spring表达式的值
        String key = SpElUtils.parseExpressionValue(striveLock.key(), variables, String.class);
        key = "lock:".concat(key);
        if (striveLock.block()) {
            int times = 0;
            boolean ok;
            while (!(ok = lock(key, striveLock.expire()))) {
                log.info("{}重试{}次", key, times + 1);
                if (++times >= striveLock.times()) {
                    break;
                }
                TimeUtils.delay(striveLock.interval() * 1000);
            }
            if (ok) {
                log.info("{}重试{}次后成功获得锁", key, times + 1);
                try {
                    return proceedingJoinPoint.proceed();
                } catch (Throwable throwable) {
                    log.error("{}获得锁后，执行方法失败", key);
                    throw throwable;
                } finally {
                    unlock(key);
                }
            } else {
                log.debug("{}获取锁失败", key);
                throw new StriveLockException();
            }
        } else {
            if (lock(key, striveLock.expire())) {
                log.debug("{}获取锁成功", key);
                try {
                    return proceedingJoinPoint.proceed();
                } catch (Throwable throwable) {
                    log.error("{}获得锁后，执行方法失败", key);
                    throw throwable;
                } finally {
                    unlock(key);
                }
            } else {
                log.debug("{}获取锁失败", key);
                throw new StriveLockException();
            }
        }
    }

    /**
     * 将键插入到redis,加锁，如果键在redis中存在，就加锁失败
     */
    private boolean lock(String key, long timeout) {
        String value = RandomStringUtils.genUUID();
        Boolean ok = redisTemplate.opsForValue()
                .setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
        return ok != null && ok;
    }

    /**
     * 删除redis中的键，解锁
     */
    private void unlock(String key) {
        redisTemplate.delete(key);
        log.debug("{}解锁成功", key);
    }

}
