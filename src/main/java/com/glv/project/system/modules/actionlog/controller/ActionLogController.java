package com.glv.project.system.modules.actionlog.controller;

import com.glv.project.system.modules.actionlog.dto.ActionLogQueryDto;
import com.glv.project.system.modules.actionlog.entity.ActionLogEntity;
import com.glv.project.system.modules.actionlog.service.ActionLogService;
import com.glv.project.system.modules.request.annotation.RestPostMapping;
import com.glv.project.system.modules.request.dto.PageRequest;
import com.glv.project.system.modules.response.dto.PageData;
import com.glv.project.system.modules.response.dto.ResponseStatusDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author ZHOUXIANG
 */
@Controller
@Api
@RequestMapping("/admin/sys/actionlog")
public class ActionLogController {

    @Resource
    private ActionLogService actionLogService;

    @ApiOperation("分页查询用户行为日志")
    @RestPostMapping("/list")
    public ResponseStatusDto<PageData<ActionLogEntity>> getLogList(
            @RequestBody PageRequest<ActionLogQueryDto> pageRequest) {
        PageData<ActionLogEntity> pageData =
                actionLogService.getLogList(pageRequest);
        return ResponseStatusDto.success(pageData);
    }

    @ApiOperation("批量删除日志")
    @RestPostMapping("/deleteBatch/{unit}")
    public ResponseStatusDto<String> deleteBatch(@PathVariable String unit) {
        actionLogService.deleteBatch(unit);
        return ResponseStatusDto.success();
    }
}
