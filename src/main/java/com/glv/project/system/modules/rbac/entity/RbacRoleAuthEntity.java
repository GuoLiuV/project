package com.glv.project.system.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.glv.project.system.modules.jpa.entity.BaseEntity;
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
@Table(name = "T_SYS_RBAC_ROLE_AUTH")
@TableName("T_SYS_RBAC_ROLE_AUTH")
public class RbacRoleAuthEntity extends BaseEntity {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色代码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

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

}
