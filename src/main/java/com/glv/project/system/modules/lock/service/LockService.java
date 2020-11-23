package com.glv.project.system.modules.lock.service;

/**
 * @author ZHOUXIANG
 */
public interface LockService {

    /**
     * 加锁
     *
     * @param key     键
     * @param timeout 超时时间
     * @return true/false
     */
    boolean lock(String key, long timeout);

    /**
     * 解锁
     *
     * @param key 键
     */
    void unlock(String key);
}
