package com.glv.project.system.modules.scheduling.dto;

import com.glv.project.system.modules.jpa.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel
public class StriveSchExecutorDto extends BaseDto {

    @ApiModelProperty("服务端唯一标识")
    @NotBlank(message = "执行器编码不能为空")
    private String code;

    @ApiModelProperty("服务端名称")
    @NotBlank(message = "执行器名称不能为空")
    private String name;

    @ApiModelProperty("服务端地址前缀")
    @NotBlank(message = "执行器地址不能为空")
    private String url;
}
