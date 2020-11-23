package com.glv.project.system.modules.scheduling.entity;

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
@Table(name = "T_SYS_SCH_EXECUTOR")
@TableName("T_SYS_SCH_EXECUTOR")
public class StriveSchExecutorEntity extends BaseEntity {

    /**
     * 服务端唯一标识
     */
    private String code;

    /**
     * 服务端名称
     */
    private String name;

    /**
     * 服务端地址前缀
     */
    private String url;
}
