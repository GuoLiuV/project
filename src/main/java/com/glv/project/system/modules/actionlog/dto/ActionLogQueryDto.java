package com.glv.project.system.modules.actionlog.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class ActionLogQueryDto extends ActionLogDto {

    @ApiModelProperty("关键字")
    private String keywords;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;
}
