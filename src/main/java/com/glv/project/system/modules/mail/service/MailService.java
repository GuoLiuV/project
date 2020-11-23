package com.glv.project.system.modules.mail.service;

import com.glv.project.system.modules.mail.exception.SendMailException;
import org.springframework.core.io.InputStreamSource;

import java.io.File;
import java.util.List;
import java.util.Map;


/**
 * 邮件发送服务
 *
 * @author ZHOUXIANG
 */
public interface MailService {

    /**
     * 发送简单的文本邮件
     *
     * @param to    收件人邮箱地址
     * @param title 邮件标题
     * @param text  邮件文本内容
     */
    void sendSimpleMail(String title, String text, String... to);

    /**
     * 发送带附件的邮件
     *
     * @param title       邮件标题
     * @param text        文本
     * @param attachments 邮件附件
     * @param to          收件人邮箱地址
     * @throws SendMailException 发送邮件异常
     */
    void sendAttachmentMail(String title, String text, List<File> attachments, String... to) throws SendMailException;

    /**
     * 发送带附件的邮件
     *
     * @param title       邮件标题
     * @param text        邮件内容
     * @param attachments 邮件附件
     * @param to          收件人邮箱地址
     * @throws SendMailException 异常
     */
    void sendAttachmentMail(String title, String text, Map<String, InputStreamSource> attachments, String... to) throws SendMailException;

    /**
     * 发送Html内容的邮件
     *
     * @param title 邮件标题
     * @param text  邮件html文本内容
     * @param to    收件人邮箱地址
     * @throws SendMailException 发送异常
     */
    void sendHtmlMail(String title, String text, String... to) throws SendMailException;
}
