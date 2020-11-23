package com.glv.project.system.modules.scheduling.config;

import com.glv.project.system.modules.mail.service.MailService;
import com.glv.project.system.modules.property.StriveProperties;
import com.glv.project.system.modules.redis.handler.RedisKeyExpirationHandler;
import com.glv.project.system.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * redis key 过期监听事件，用于监控调度器是否正常运行
 * @author ZHOUXIANG
 */
@Slf4j
@Component("monitor:scheduler")
public class SchedulerInterruptHandler implements RedisKeyExpirationHandler {

    @Resource
    private StriveProperties striveProperties;

    @Resource
    private MailService mailService;

    @Override
    public void handle(String keyName) {
        try {
            if (StringUtils.isNotBlank(striveProperties.getSchedEmail())) {
                // 邮件通知超管处理
                mailService.sendSimpleMail("STRIVE框架提醒",
                        "调度器停止运行，请立即处理！",
                        striveProperties.getSchedEmail());
            }
        } catch (Exception e) {
            log.error("调度器停止运行发送邮件失败：{}", e.getMessage());
        }
        log.info("调度器已停止运行，请尽快修复!");
    }
}
