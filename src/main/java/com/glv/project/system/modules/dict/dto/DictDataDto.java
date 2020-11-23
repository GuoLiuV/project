package com.glv.project.system.modules.dict.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author ZHOUXIANG
 */
@Data
@Accessors(chain = true)
@ApiModel
public class DictDataDto {

    @ApiModelProperty(value = "id", example = "1")
    private Long id;

    @ApiModelProperty(value = "字典代码", example = "REGION")
    @NotBlank(message = "字典代码不能为空")
    private String dictCode;

    @ApiModelProperty(value = "字典值", example = "区域")
    @NotBlank(message = "字典值不能为空")
    private String dictValue;

    @ApiModelProperty(value = "字典项附加信息", example = "1")
    private String addValue;

    @ApiModelProperty(value = "字典项说明", example = "表示某个区域")
    private String dictDesc;

    @ApiModelProperty(value = "父级字典项目的ID", example = "1")
    private Long parentId;

    @ApiModelProperty(value = "序号", example = "1")
    private Double sequence;

}
