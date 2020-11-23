package com.glv.project.system.modules.scheduling.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.glv.project.system.modules.jpa.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "T_SYS_SCH_JOB")
@TableName("T_SYS_SCH_JOB")
public class StriveSchJobEntity extends BaseEntity {

    /**
     * 执行器ID
     */
    private Long execId;

    /**
     * 执行器名称
     */
    private String execName;

    /**
     * 作业编号
     */
    private String code;

    /**
     * 作业名称
     */
    private String name;

    /**
     * 作业端点
     */
    private String endpoint;

    /**
     * cron 表达式
     */
    private String cron;

    /**
     * 负责人姓名
     */
    private String person;

    /**
     * 邮件通知
     */
    private String email;

    /**
     * 作业参数
     */
    private String params;

    /**
     * 任务是否启动，RUNNING,STOP
     * @see com.glv.project.system.modules.scheduling.enums.JobStatusEnum
     */
    private String status;

    /**
     * 重试次数
     */
    private Integer retry;

    /**
     * 任务描述
     */
    @Column(columnDefinition = "text")
    private String jobDesc;

    /**
     * 下一次执行时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date nextTime;

}
