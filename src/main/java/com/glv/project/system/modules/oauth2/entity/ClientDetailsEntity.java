package com.glv.project.system.modules.oauth2.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.glv.project.system.modules.jpa.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Oauth2 客户端认证信息数据库实体
 *
 * @author ZHOUXIANG
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "T_SYS_AUTH_CLIENT")
@TableName("T_SYS_AUTH_CLIENT")
public class ClientDetailsEntity extends BaseEntity {

    /**
     * 客户端应用ID
     */
    @Column(columnDefinition = "varchar(255) not null", unique = true)
    private String clientId;

    /**
     * 客户端密码串
     */
    @Column(columnDefinition = "varchar(255)")
    private String clientSecret;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 以逗号分隔的多个资源ID
     */
    @Column(columnDefinition = "varchar(500)")
    private String resourceIds;

    /**
     * 以逗号分隔的客户端范围
     */
    @Column(columnDefinition = "varchar(500)")
    private String scopes;

    /**
     * 以逗号分隔的权限类型
     */
    @Column(columnDefinition = "varchar(500)")
    private String authorizedGrantTypes;

    /**
     * 以逗号分隔的注册重定向的Uri
     */
    @Column(columnDefinition = "varchar(500)")
    private String registeredRedirectUris;

    /**
     * 以逗号分隔的客户端所拥有的权限
     */
    @Column(columnDefinition = "varchar(500)")
    private String authorities;

    /**
     * 自动同意的范围，以逗号人隔
     */
    @Column(columnDefinition = "varchar(500)")
    private String autoApproveScopes;

    /**
     * token访问的有效时间， 以秒以单位
     */
    private Integer accessTokenValiditySeconds = 1800;

    /**
     * 刷新token有效时间， 以秒以单位
     */
    private Integer refreshTokenValiditySeconds = 60;

}
