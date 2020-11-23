package com.glv.project.system.modules.rbac.dto;

import com.glv.project.system.modules.jpa.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description = "组织接口模型类")
public class RbacOrgDto extends BaseDto {

    @ApiModelProperty(value = "组织ID，修改时必需", example = "1")
    private Long id;

    @ApiModelProperty(value = "组织代码", example = "XX_COMPANY")
    @NotNull(message = "组织代码不能为空")
    private String orgCode;

    @ApiModelProperty(value = "组织名称", example = "XXX公司")
    @NotNull(message = "组织名称不能为空")
    private String orgName;

    @ApiModelProperty("组织代号")
    private String orgNum;

    @ApiModelProperty(value = "组织别名", example = "XX公司")
    @NotNull(message = "组织别名不能为空")
    private String aliasName;

    @ApiModelProperty(value = "组织类型代码，字典表", example = "公司")
    @NotNull(message = "组织类型不能为空")
    private String orgTypeCode;

    @ApiModelProperty("组织类型名称")
    private String orgTypeName;

    @ApiModelProperty(value = "父组织ID", example = "3")
    private Long parentId;

    @ApiModelProperty(value = "序号", example = "1")
    private Double sequence;

}
