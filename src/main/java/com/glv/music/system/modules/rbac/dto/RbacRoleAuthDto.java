package com.glv.music.system.modules.rbac.dto;

import com.glv.music.system.modules.jpa.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class RbacRoleAuthDto extends BaseDto {

    @ApiModelProperty("角色ID")
    @NotNull(message = "角色ID为空")
    private Long roleId;

    @ApiModelProperty("权限ID列表")
    private List<Long> authIds;
}
