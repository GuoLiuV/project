package com.glv.project.system.modules.exception;

/**
 * 框架全局异常类
 *
 * @author ZHOUXIANG
 */
@SuppressWarnings("unused")
public class StriveException extends RuntimeException {

    public StriveException() {
        super();
    }

    public StriveException(String message) {
        super(message);
    }

    public StriveException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
