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
@ApiModel
public class RbacUserRegDto {

    @ApiModelProperty("用户名")
    @NotBlank(message = "登录名不能为空")
    private String loginName;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("确认密码")
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    @ApiModelProperty("集团/公司代码")
    @NotBlank(message = "集团/公司代码不能为空")
    private String orgCode;

    @ApiModelProperty("集团/公司名称")
    @NotBlank(message = "集团/公司名称不能为空")
    private String orgName;

    @ApiModelProperty("集团/公司简称")
    @NotBlank(message = "集团/公司简称不能为空")
    private String orgAliasName;
}
