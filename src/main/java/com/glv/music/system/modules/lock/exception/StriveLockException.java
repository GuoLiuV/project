package com.glv.music.system.modules.lock.exception;

import com.glv.music.system.modules.exception.StriveException;

/**
 * 基于Redis分布式加锁失败时，抛出的异常
 *
 * @author ZHOUXIANG
 */
public class StriveLockException extends StriveException {

    public StriveLockException() {
        super("获取Redis锁失败");
    }

}
