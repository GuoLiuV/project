package com.glv.project.system.modules.rbac.dto;

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
@ApiModel(description = "角色接口模型")
public class RbacRoleDto {

    @ApiModelProperty(value = "id", example = "1")
    private Long id;

    @ApiModelProperty(value = "角色代码",example = "ADMIN")
    @NotBlank(message = "角色代码不能为空")
    private String roleCode;

    @ApiModelProperty(value = "角色名称", example = "管理员")
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @ApiModelProperty(value = "角色别名", example = "管理员")
    private String aliasName;

    @ApiModelProperty(value = "角色附加信息", example = "1")
    private String addInfo;

    @ApiModelProperty(value = "角色权限操作描述")
    private String roleDesc;

    @ApiModelProperty(value = "序号", example = "1")
    private Double sequence;

}
