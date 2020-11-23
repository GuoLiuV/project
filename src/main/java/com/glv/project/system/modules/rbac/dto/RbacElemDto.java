package com.glv.project.system.modules.rbac.dto;

import com.glv.project.system.modules.jpa.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class RbacElemDto extends BaseDto {

    @ApiModelProperty("元素代码")
    private String elemCode;

    @ApiModelProperty("元素名称")
    private String elemName;

    @ApiModelProperty("元素别名")
    private String aliasName;

    @ApiModelProperty("附加信息")
    private String addInfo;

    @ApiModelProperty("元素说明")
    private String elemDesc;

    @ApiModelProperty("序号")
    private Double sequence;
}
