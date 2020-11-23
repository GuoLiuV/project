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
@ApiModel(description = "用户接口模型")
public class RbacUserDto {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("登录名")
    @NotBlank(message = "登录名不能为空，微信用户可以随机生成")
    private String loginName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户代码")
    private String userCode;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("性别")
    private String sexCode;

    @ApiModelProperty("身份证号")
    private String cardId;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("邮箱地址")
    private String email;

    @ApiModelProperty("联系地址")
    private String address;

    @ApiModelProperty("手机绑定")
    private String mobileDeviceId;

    @ApiModelProperty("Pad绑定")
    private String padDeviceId;

    @ApiModelProperty("小程序的OpenID")
    private String proOpenId;

    @ApiModelProperty("公众号的OpenID")
    private String pubOpenId;

    @ApiModelProperty("微信开放平台unionId")
    private String unionId;

    @ApiModelProperty("身份认证Key的ID")
    private String keyId;

    @ApiModelProperty("用户图像地址")
    private String avatar;

    @ApiModelProperty("查询时用于指定组织id")
    private Long orgId = -1L;

    /**
     * 注册时为Y
     *
     * @see com.glv.project.system.enums.OperateStatusEnum
     */
    @ApiModelProperty("用户是否是租户管理员")
    private String isTenant;

    @ApiModelProperty(value = "序号", example = "1")
    private Double sequence;

}
