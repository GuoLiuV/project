package com.glv.project.system.modules.accesslog.dto;

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
public class AccessLogDto {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("请求来源IP")
    private String host;

    @ApiModelProperty("请求的用户名")
    private String user;

    @ApiModelProperty("来源地址")
    private String referrer;

    @ApiModelProperty("客户端浏览器信息")
    private String userAgent;

    @ApiModelProperty("请求路径")
    private String path;

    @ApiModelProperty("HTTP请求方法")
    private String method;

    @ApiModelProperty("HTTP协议")
    private String protocol;

}
