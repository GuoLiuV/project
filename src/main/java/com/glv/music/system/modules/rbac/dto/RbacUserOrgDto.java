package com.glv.music.system.modules.rbac.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 批量用户更改组织接口模型
 *
 * @author ZHOUXIANG
 */
@Data
@Accessors(chain = true)
@ApiModel
public class RbacUserOrgDto {

    @ApiModelProperty("组织ID")
    @NotNull(message = "组织ID不能为空")
    private Long orgId;

    @ApiModelProperty("用户ID列表")
    private List<Long> userIds;

}
