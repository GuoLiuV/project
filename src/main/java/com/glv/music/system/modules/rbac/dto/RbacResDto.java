package com.glv.music.system.modules.rbac.dto;

import com.glv.music.system.modules.jpa.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class RbacResDto extends BaseDto {

    @ApiModelProperty("资源地址")
    private String resUri;

    @ApiModelProperty("资源名称")
    private String resName;

    @ApiModelProperty("资源别名")
    private String aliasName;

    @ApiModelProperty("附加信息")
    private String addInfo;

    @ApiModelProperty("资源说明")
    private String resDesc;

    @ApiModelProperty("序号")
    private Double sequence;
}
