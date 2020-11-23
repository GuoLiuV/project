package com.glv.project.system.modules.drools.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.glv.project.system.modules.jpa.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * drools规则内容表
 *
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "T_SYS_DROOLS_RULE")
@TableName("T_SYS_DROOLS_RULE")
public class DroolsRuleEntity extends BaseEntity {

    /**
     * 规则编号
     */
    @Column(unique = true)
    private String ruleCode;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 规则内容定义
     */
    @Column(columnDefinition = "text")
    private String ruleContent;

    /**
     * 规则版本
     */
    private Integer version;
}
