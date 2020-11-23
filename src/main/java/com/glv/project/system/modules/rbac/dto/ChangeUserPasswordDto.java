package com.glv.project.system.modules.rbac.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Data
@Accessors(chain = true)
@ApiModel
public class ChangeUserPasswordDto {

    @ApiModelProperty("用户ID")
    private List<Long> userIds;

    @ApiModelProperty("登录名")
    private String loginName;

    @ApiModelProperty("用户原来的密码")
    private String originPassword;

    @ApiModelProperty("用户修改的密码")
    @NotBlank(message = "要修改的密码不能为空")
    private String password;

    @ApiModelProperty("确认要修改的密码")
    private String confirmPassword;

    @ApiModelProperty("是否置密码")
    private boolean reset;

}
