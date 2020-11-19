package com.glv.music.system.modules.drools.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel
public class DroolsRuleExecDto {

    @ApiModelProperty("规则编号")
    private String ruleCode;

    @ApiModelProperty("规则fact数据")
    private Map<String, Object> ruleData;
}
