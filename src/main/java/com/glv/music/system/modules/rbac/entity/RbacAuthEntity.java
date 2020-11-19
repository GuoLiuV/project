package com.glv.music.system.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.glv.music.system.modules.jpa.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "T_SYS_RBAC_AUTH")
@TableName("T_SYS_RBAC_AUTH")
public class RbacAuthEntity extends BaseEntity {

    /**
     * 权限代码
     */
    private String authCode;

    /**
     * 权限名称
     */
    private String authName;

    /**
     * 权限别名
     */
    private String aliasName;

    /**
     * 附加信息
     */
    private String addInfo;

    /**
     * 权限说明
     */
    private String authDesc;

    /**
     * 权限类型代码
     */
    private String authTypeCode;

    /**
     * 权限类型名称
     */
    private String authTypeName;

    /**
     * 序号
     */
    private Double sequence;
}
