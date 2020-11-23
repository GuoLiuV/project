package com.glv.project.system.modules.dict.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.glv.project.system.modules.jpa.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "T_SYS_DICT_DATA")
@TableName("T_SYS_DICT_DATA")
public class DictDataEntity extends BaseEntity {

    /**
     * 字典代码
     */
    @Column(unique = true)
    private String dictCode;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 字典项附加值
     */
    private String addValue;

    /**
     * 字典项说明
     */
    private String dictDesc;

    /**
     * 父级字典项的ID
     */
    private Long parentId;

    /**
     * 序号
     */
    private Double sequence;
}
