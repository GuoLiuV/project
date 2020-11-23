package com.glv.project.system.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.glv.project.system.modules.jpa.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 组织机构表
 *
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "T_SYS_RBAC_ORG")
@TableName("T_SYS_RBAC_ORG")
public class RbacOrgEntity extends BaseEntity {

    /**
     * 组织机构代码
     */
    private String orgCode;

    /**
     * 组织机构名称
     */
    private String orgName;

    /**
     * 组织机构别名
     */
    private String aliasName;

    /**
     * 组织类型
     * @see com.glv.project.system.enums.OrgTypeEnum
     */
    private String orgTypeCode;

    /**
     * 组织类型名称
     */
    private String orgTypeName;

    /**
     * 组织代号
     */
    private String orgNum;

    /**
     * 组织机构层级ID
     */
    private String treeId;

    /**
     * 父组织ID
     */
    private Long parentId;

    /**
     * 序号
     */
    private Double sequence;

}
