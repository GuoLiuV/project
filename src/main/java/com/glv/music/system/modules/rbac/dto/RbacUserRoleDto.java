package com.glv.music.system.modules.rbac.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Data
@Accessors(chain = true)
@ApiModel
public class RbacUserRoleDto {

    @ApiModelProperty("用户ID")
    @NotEmpty(message = "用户ID为空")
    private List<Long> userIds;

    @ApiModelProperty("角色ID列表")
    private List<Long> roleIds;

}
