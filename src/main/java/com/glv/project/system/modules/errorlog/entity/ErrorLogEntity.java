package com.glv.project.system.modules.errorlog.entity;


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
@Table(name = "T_SYS_LOG_ERROR")
@TableName("T_SYS_LOG_ERROR")
public class ErrorLogEntity extends BaseEntity {

    /**
     * 操作用户的登录名
     */
    private String user;

    /**
     * 异常信息
     */
    private String message;

    /**
     * 异常堆栈信息
     */
    @Column(columnDefinition = "text")
    private String stackInfo;

    /**
     * 请求的路径
     */
    private String path;

}
