package com.glv.project.system.modules.scheduling.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.glv.project.system.modules.jpa.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel
public class StriveSchRecordDto extends BaseDto {

    @ApiModelProperty("作业ID")
    private Long jobId;

    @ApiModelProperty("作业名称")
    private String jobName;

    @ApiModelProperty("执行状态")
    private String status;

    @ApiModelProperty("异常堆栈信息")
    private String stackTrace;

    @ApiModelProperty("执行开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty("执行结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

}
