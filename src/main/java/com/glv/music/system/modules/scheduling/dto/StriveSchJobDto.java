package com.glv.music.system.modules.scheduling.dto;

import com.glv.music.system.modules.jpa.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel
public class StriveSchJobDto extends BaseDto {

    @ApiModelProperty("执行器ID")
    @NotNull(message = "执行器不能为空")
    private Long execId;

    @ApiModelProperty("执行器名称")
    private String execName;

    @ApiModelProperty("作业编号")
    @NotBlank(message = "作业编号不能为空")
    private String code;

    @ApiModelProperty("作业名称")
    @NotBlank(message = "作业名称不能为空")
    private String name;

    @ApiModelProperty("作业端点")
    @NotBlank(message = "作业端点不能为空")
    private String endpoint;

    @ApiModelProperty("cron 表达式")
    @NotBlank(message = "cron表达式不能为空")
    private String cron;

    @ApiModelProperty("负责人姓名")
    private String person;

    @ApiModelProperty("邮件通知")
    private String email;

    @ApiModelProperty("作业参数")
    private String params;

    @ApiModelProperty("作业状态")
    private String status;

    @ApiModelProperty("重试次数")
    private Integer retry;

    @ApiModelProperty("任务描述")
    private String jobDesc;
}
