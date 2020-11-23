package com.glv.project.system.modules.oauth2.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ZHOUXIANG
 */
@Data
@Accessors(chain = true)
@ApiModel
public class ClientDetailsDto {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("客户端ID,appId")
    private String clientId;

    @ApiModelProperty("客户端密钥，appSecret")
    @NotNull(message = "客户端密钥不能为空")
    private String clientSecret;

    @ApiModelProperty("客户端名称")
    @NotBlank(message = "客户端名称不能为空")
    private String clientName;

    @ApiModelProperty("以逗号分隔的资源ID")
    private String resourceIds;

    @ApiModelProperty(value = "以逗号分隔的客户端权限范围", example = "request")
    private String scopes;

    @ApiModelProperty(value = "支持的授权类型", example = "password,client_credentials")
    private String authorizedGrantTypes;

    @ApiModelProperty("以逗号分隔的注册重定向的Uri，在authrization_code模式有效")
    private String registeredRedirectUris;

    @ApiModelProperty("以逗号分隔的客户端所拥有的权限")
    private String authorities;

    @ApiModelProperty("自动同意的范围，以逗号人隔")
    private String autoApproveScopes;

    @ApiModelProperty("token访问的有效时间， 以秒以单位")
    private Integer accessTokenValiditySeconds;

    @ApiModelProperty("刷新token有效时间， 以秒以单位")
    private Integer refreshTokenValiditySeconds;
}
