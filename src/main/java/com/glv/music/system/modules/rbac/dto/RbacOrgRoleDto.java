package com.glv.music.system.modules.rbac.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Data
@Accessors(chain = true)
@ApiModel
public class RbacOrgRoleDto {

    @ApiModelProperty("组织ID")
    @NotNull(message = "组织ID为空")
    private Long orgId;

    @ApiModelProperty("角色ID列表")
    private List<Long> roleIds;

}
