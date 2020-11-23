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
public class RbacAuthDto extends BaseDto {

    @ApiModelProperty("权限代码")
    @NotBlank(message = "权限代码为空")
    private String authCode;

    @ApiModelProperty("权限名称")
    @NotBlank(message = "权限名称为空")
    private String authName;

    @ApiModelProperty("权限别名")
    private String aliasName;

    @ApiModelProperty("权限附加信息")
    private String addInfo;

    @ApiModelProperty("权限说明")
    private String authDesc;

    @ApiModelProperty("权限类型代码")
    @NotBlank(message = "权限类型为空")
    private String authTypeCode;

    @ApiModelProperty("权限类型名称")
    private String authTypeName;

    @ApiModelProperty("序号")
    private Double sequence;
}
