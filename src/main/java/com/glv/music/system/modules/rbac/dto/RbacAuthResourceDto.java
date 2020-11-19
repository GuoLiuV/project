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
public class RbacAuthResourceDto extends BaseDto {

    @ApiModelProperty("权限ID")
    @NotNull(message = "权限ID为空")
    private Long authId;

    @ApiModelProperty("其他资源ID列表")
    private List<Long> resourceIds;
}
