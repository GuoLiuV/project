package com.glv.project.system.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.glv.project.system.modules.jpa.entity.BaseEntity;
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
@Table(name = "T_SYS_RBAC_RES")
@TableName("T_SYS_RBAC_RES")
public class RbacResEntity extends BaseEntity {

    /**
     * 资源地址
     */
    private String resUri;

    /**
     * 资源名称
     */
    private String resName;

    /**
     * 资源别名
     */
    private String aliasName;

    /**
     * 附加信息
     */
    private String addInfo;

    /**
     * 资源说明
     */
    private String resDesc;

    /**
     * 序号
     */
    private Double sequence;
}
