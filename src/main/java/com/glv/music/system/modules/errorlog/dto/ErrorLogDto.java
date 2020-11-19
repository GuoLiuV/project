package com.glv.music.system.modules.errorlog.dto;

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
public class ErrorLogDto {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户登录名")
    private String user;

    @ApiModelProperty("异常信息")
    private String message;

    @ApiModelProperty("异常堆栈信息")
    private String stackInfo;

    @ApiModelProperty("请求的路径")
    private String path;

}
