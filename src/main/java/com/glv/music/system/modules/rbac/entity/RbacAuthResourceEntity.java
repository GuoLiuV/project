package com.glv.music.system.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.glv.music.system.modules.jpa.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 角色权限关联表
 *
 * @author ZHOUXIANG
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "T_SYS_RBAC_AUTH_RESOURCE")
@TableName("T_SYS_RBAC_AUTH_RESOURCE")
public class RbacAuthResourceEntity extends BaseEntity {

    /**
     * 权限ID
     */
    private Long authId;

    /**
     * 权限代码
     */
    private String authCode;

    /**
     * 权限名称
     */
    private String authName;

    /**
     * 权限类型代码
     */
    private String authTypeCode;

    /**
     * 权限类型名称
     */
    private String authTypeName;

    /**
     * 相关资源的ID
     */
    private Long resId;

    /**
     * 相关资源的代码或uri
     */
    private String code;

    /**
     * 相关资源的名称
     */
    private String name;

}
