package com.glv.music.system.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.glv.music.system.modules.jpa.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户角色关联表
 *
 * @author ZHOUXIANG
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "T_SYS_RBAC_USER_ROLE")
@TableName("T_SYS_RBAC_USER_ROLE")
public class RbacUserRoleEntity extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

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

}
