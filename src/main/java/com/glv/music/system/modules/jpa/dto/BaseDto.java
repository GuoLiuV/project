package com.glv.music.system.modules.jpa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据库公共实体抽象类
 *
 * @author Oscar
 */
@Data
@Accessors(chain = true)
@ApiModel
public abstract class BaseDto implements Serializable {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("创建人ID")
    private Long createUserId;

    @ApiModelProperty("更新人ID")
    private Long updateUserId;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("最后更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("不使用逻辑删除")
    private String deleted = "0";

    @ApiModelProperty("租户ID")
    private Long tenantId;

}