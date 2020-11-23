package com.glv.project.system.modules.exception;

/**
 * 未授权异常
 *
 * @author ZHOUXIANG
 */
public class StriveNoAuthException extends RuntimeException {

    public StriveNoAuthException() {
        super();
    }

    public StriveNoAuthException(String message) {
        super(message);
    }

    public StriveNoAuthException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
