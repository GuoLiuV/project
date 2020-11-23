package com.glv.project.system.modules.mail.service.impl;

import com.glv.project.system.modules.mail.exception.SendMailException;
import com.glv.project.system.modules.mail.service.MailService;
import com.glv.project.system.modules.property.StriveProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Map;


/**
 * 邮件发送服务
 *
 * @author ZHOUXIANG
 */
@Service
public class MailServiceImpl implements MailService {

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private StriveProperties striveProperties;

    @Override
    public void sendSimpleMail(String title, String text, String... to) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(striveProperties.getMailSendUser());
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendAttachmentMail(String title, String text, List<File> attachments, String... to) throws SendMailException {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, true);
            helper.setFrom(striveProperties.getMailSendUser());
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(text, true);
            FileSystemResource file;
            String fileName;
            if (attachments != null && attachments.size() > 0) {
                for (File f : attachments) {
                    file = new FileSystemResource(f);
                    fileName = f.getName();
                    //添加附件
                    helper.addAttachment(fileName, file);
                }
            }
            javaMailSender.send(mimeMailMessage);
        } catch (MessagingException e) {
            throw new SendMailException(e.getMessage());
        }
    }

    @Override
    public void sendAttachmentMail(String title, String text, Map<String, InputStreamSource> attachments, String... to) throws SendMailException {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, true);
            helper.setFrom(striveProperties.getMailSendUser());
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(text, true);
            if (attachments != null && attachments.size() > 0) {
                for (Map.Entry<String, InputStreamSource> f : attachments.entrySet()) {
                    //添加附件
                    helper.addAttachment(f.getKey(), f.getValue());
                }
            }
            javaMailSender.send(mimeMailMessage);
        } catch (MessagingException e) {
            throw new SendMailException(e.getMessage());
        }
    }

    @Override
    public void sendHtmlMail(String title, String text, String... to) throws SendMailException {
        this.sendAttachmentMail(title, text, (List<File>) null, to);
    }
}
