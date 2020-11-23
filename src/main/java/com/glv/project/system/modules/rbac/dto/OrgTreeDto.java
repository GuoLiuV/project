package com.glv.project.system.modules.rbac.dto;

import com.glv.project.system.modules.rbac.entity.RbacOrgEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "组织树接口模型")
public class OrgTreeDto {

    @ApiModelProperty(value = "树节点名称")
    private String title;

    @ApiModelProperty("是否展开")
    private boolean expand = false;

    @ApiModelProperty("是否选中")
    private boolean selected = false;

    @ApiModelProperty(value = "附加数据组织ID")
    private RbacOrgEntity data;

    @ApiModelProperty(value = "是否禁用选择框")
    private boolean disable = false;

    @ApiModelProperty(value = "子节点数据")
    private List<OrgTreeDto> children;

}
