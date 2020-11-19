package com.glv.music.system.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.glv.music.system.modules.jpa.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 角色表
 *
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "T_SYS_RBAC_ROLE")
@TableName("T_SYS_RBAC_ROLE")
public class RbacRoleEntity extends BaseEntity {

    /**
     * 角色代码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色别名
     */
    private String aliasName;

    /**
     * 角色附加信息
     */
    private String addInfo;

    /**
     * 角色说明
     */
    private String roleDesc;

    /**
     * 序号
     */
    private Double sequence;
}
