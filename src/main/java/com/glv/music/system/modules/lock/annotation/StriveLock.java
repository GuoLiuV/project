package com.glv.music.system.modules.lock.annotation;

import java.lang.annotation.*;

/**
 * 在方法上使用此注解，就处理Redis锁，
 * key 是键
 * @author ZHOUXIANG
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface StriveLock {

    /**
     * 指定要进行加锁的键，框架会将此键（值随机）存入Redis，
     * 如果成功设置此键，则加锁成功，如此Key已经存在则加锁失败
     * @return key
     */
    String key() default "";

    /**
     * 是否启用阻塞，黙认不启用，则访问如果加锁失败，
     * 直接抛出加锁失败异常，黙认不阻塞
     */
    boolean block() default false;

    /**
     * 定义阻塞重试时间间隔，单位为秒，黙认1s
     */
    int interval() default 1;

    /**
     * 阻塞时重试次数，超出此范围则抛出异常返回，黙认3次
     */
    int times() default 3;

    /**
     * 锁过期时间，以秒为单位，黙认30秒
     */
    long expire() default 30;
}
