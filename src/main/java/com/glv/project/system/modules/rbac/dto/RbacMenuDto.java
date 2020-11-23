package com.glv.project.system.modules.rbac.dto;

import com.glv.project.system.modules.jpa.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class RbacMenuDto extends BaseDto {

    @ApiModelProperty("菜单代码")
    @NotBlank(message = "菜单编码为空")
    private String menuCode;

    @ApiModelProperty("菜单名称")
    @NotBlank(message = "菜单名称为空")
    private String menuName;

    @ApiModelProperty("菜单别名")
    private String aliasName;

    @ApiModelProperty("附加信息")
    private String addInfo;

    @ApiModelProperty("菜单说明")
    private String menuDesc;

    @ApiModelProperty("序号")
    private Double sequence;
}
