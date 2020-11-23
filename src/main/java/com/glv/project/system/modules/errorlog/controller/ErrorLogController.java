package com.glv.project.system.modules.errorlog.controller;

import com.glv.project.system.modules.errorlog.dto.ErrorLogQueryDto;
import com.glv.project.system.modules.errorlog.entity.ErrorLogEntity;
import com.glv.project.system.modules.errorlog.service.ErrorLogService;
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
@RequestMapping("/admin/sys/errorlog")
public class ErrorLogController {

    @Resource
    private ErrorLogService errorLogService;

    @ApiOperation("分页查询系统错误日志")
    @RestPostMapping("/list")
    public ResponseStatusDto<PageData<ErrorLogEntity>> getLogList(
            @RequestBody PageRequest<ErrorLogQueryDto> pageRequest) {
        PageData<ErrorLogEntity> pageData = errorLogService.getLogList(pageRequest);
        return ResponseStatusDto.success(pageData);
    }

    @ApiOperation("批量删除日志")
    @RestPostMapping("/deleteBatch/{unit}")
    public ResponseStatusDto<String> deleteBatch(@PathVariable String unit) {
        errorLogService.deleteBatch(unit);
        return ResponseStatusDto.success();
    }

}
