package com.glv.project.system.modules.holiday.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 节假日配置接口交换数据
 *
 * @author ZHOUXIANG
 */
@Data
@Accessors(chain = true)
@ApiModel
public class HolidayConfigDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "节假日年份", example = "2019")
    private Integer year;

    @ApiModelProperty(value = "日期字符串", example = "2019-08-09")
    @NotBlank(message = "节假日日期不能为空")
    private String date;

    @ApiModelProperty(value = "节假日类型，放假或调班", example = "HOLIDAY")
    @NotBlank(message = "节假日类型不能为空")
    private String type;

}
