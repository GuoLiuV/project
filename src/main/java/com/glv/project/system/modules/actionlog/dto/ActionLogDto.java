package com.glv.project.system.modules.actionlog.dto;

import com.glv.project.system.enums.OperateStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ZHOUXIANG
 */
@Data
@Accessors(chain = true)
@ApiModel
public class ActionLogDto {

    @ApiModelProperty(value = "id", example = "1")
    public Long id;

    @ApiModelProperty(value = "操作人姓名", example = "千与千寻")
    private String user;

    @ApiModelProperty(value = "操作主题，手动输入", example = "插入")
    private String title;

    @ApiModelProperty(value = "操作的方法（注解的方法）", example = "saveOrg")
    private String method;

    /**
     * @see OperateStatusEnum
     */
    @ApiModelProperty(value = "操作状态，是否操作成功", example = "Y")
    private String status;

    @ApiModelProperty(value = "操作入口，即Request中的请求地址", example = "/admin/save")
    private String path;

}
