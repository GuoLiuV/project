package com.glv.music.system.modules.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * strive.properties发展配置文件对应的Bean
 *
 * @author ZHOUXIANG
 */
@Data
@Component
@PropertySource(value = "classpath:application.yml", encoding = "UTF-8")
public class StriveProperties {

    /**
     * 应用名称
     */
    @Value("${strive.name}")
    private String appName;

    /**
     * webservice 地址
     */
    @Value("${strive.webservice.address}")
    private String wsUrl;

    /**
     * 邮件发送用户名
     */
    @Value("${strive.mail.send.username}")
    private String mailSendUser;

    /**
     * 配置文件上传路径
     */
    @Value("${strive.upload.path}")
    private String uploadPath;

    /**
     * 临时文件目录
     */
    @Value("${strive.temp.path}")
    private String tempPath;

    /**
     * 是否启用框架缓存
     */
    @Value("${strive.cache.enabled}")
    private boolean cacheEnabled;

    /**
     * 是否启用Redis缓存
     */
    @Value("${strive.cache.ehcache.enabled}")
    private boolean ehcacheCacheEnabled;

    /**
     * 是否启用Redis缓存
     */
    @Value("${strive.cache.redis.enabled}")
    private boolean redisCacheEnabled;

    /**
     * 是否启用分布式锁
     */
    @Value("${strive.lock.enabled}")
    private boolean lockEnabled;

    /**
     * 是否启用注解方式的户行为日志记录
     */
    @Value("${strive.log.action.enabled}")
    private boolean actionLogEnabled;

    /**
     * 是否启用用户访问日志记录
     */
    @Value("${strive.log.access.enabled}")
    private boolean accessLogEnabled;

    /**
     * 是否开户错误日志记录
     */
    @Value("${strive.log.error.enabled}")
    private boolean errorLogEnabled;

    /**
     * 是否启用redis存储token信息
     */
    @Value("${strive.oauth2.redis.enabled}")
    private boolean redisTokenEnabled;

    /**
     * 是否运行调度器
     */
    @Value("${strive.sched.enabled}")
    private boolean schedEnabled;

    /**
     * 调度器同时执行任务数量
     */
    @Value("${strive.sched.thread-count}")
    private int schedThreadCount;

    /**
     * 调试器停止通知邮箱
     */
    @Value("${strive.sched.email}")
    private String schedEmail;
}
