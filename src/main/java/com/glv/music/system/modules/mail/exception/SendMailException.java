package com.glv.music.system.modules.mail.exception;


import com.glv.music.system.modules.exception.StriveException;

/**
 * 自定义邮件发送异常
 *
 * @author ZHOUXIANG
 */
public class SendMailException extends StriveException {

    public SendMailException() {
        super("邮件发送时异常");
    }

    public SendMailException(String message) {
        super("邮件发送时异常: " + message);
    }
}
