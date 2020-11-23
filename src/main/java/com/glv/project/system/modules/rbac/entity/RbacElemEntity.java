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
@Table(name = "T_SYS_RBAC_ELEM")
@TableName("T_SYS_RBAC_ELEM")
public class RbacElemEntity extends BaseEntity {

    /**
     * 元素代码
     */
    private String elemCode;

    /**
     * 元素名称
     */
    private String elemName;

    /**
     * 元素别名
     */
    private String aliasName;

    /**
     * 附加信息
     */
    private String addInfo;

    /**
     * 元素说明
     */
    private String elemDesc;

    /**
     * 序号
     */
    private Double sequence;
}
