package com.glv.project.system.modules.scheduling.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.glv.project.system.modules.jpa.entity.BaseEntity;
import com.glv.project.system.modules.scheduling.enums.JobStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 作业调度记录
 *
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "T_SYS_SCH_RECORD")
@TableName("T_SYS_SCH_RECORD")
public class StriveSchRecordEntity extends BaseEntity {

    /**
     * 作业ID
     */
    private Long jobId;

    /**
     * 作业名称
     */
    private String jobName;

    /**
     * 执行状态
     * @see JobStatusEnum
     */
    private String status;

    /**
     * 异常堆栈信息
     */
    @Column(columnDefinition = "text")
    private String stackTrace;

    /**
     * 执行开始时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 执行结束时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
}
