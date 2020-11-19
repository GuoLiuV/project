package com.glv.music.system.modules.drools.dto;

import com.glv.music.system.modules.jpa.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel
public class DroolsRuleDto extends BaseDto {

    @ApiModelProperty("规则编号")
    private String ruleCode;

    @ApiModelProperty("规则名称")
    private String ruleName;

    @ApiModelProperty("规则内容定义")
    private String ruleContent;

    @ApiModelProperty("规则版本")
    private Integer version;
}
