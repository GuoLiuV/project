package com.glv.project.system.modules.dict.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ZHOUXIANG
 */
@Data
@Accessors(chain = true)
@ApiModel
public class DictDataRequestDto {

    @ApiModelProperty("字典类型代码")
    private String parentCode;

    @ApiModelProperty("根据字典值模糊查询")
    private String dictValue;

    @ApiModelProperty("附加值查询")
    private String addValue;
}
