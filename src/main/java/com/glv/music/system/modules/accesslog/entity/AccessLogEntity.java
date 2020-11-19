package com.glv.music.system.modules.accesslog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.glv.music.system.modules.jpa.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "T_SYS_LOG_ACCESS")
@TableName("T_SYS_LOG_ACCESS")
public class AccessLogEntity extends BaseEntity {

    /**
     * 请求来源IP
     */
    private String host;

    /**
     * 请求的用户名
     */
    private String user;

    /**
     * 来源地址
     */
    private String referrer;

    /**
     * 浏览器信息
     */
    @Column(columnDefinition = "varchar(1000)")
    private String userAgent;

    /**
     * 请求路径
     */
    private String path;

    /**
     * HTTP请求方法
     */
    private String method;

    /**
     * HTTP协议
     */
    private String protocol;

}
