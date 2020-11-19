package com.glv.music.system.modules.accesslog.controller;

import com.glv.music.system.modules.accesslog.dto.AccessLogQueryDto;
import com.glv.music.system.modules.accesslog.entity.AccessLogEntity;
import com.glv.music.system.modules.accesslog.service.AccessLogService;
import com.glv.music.system.modules.request.annotation.RestPostMapping;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import com.glv.music.system.modules.response.dto.ResponseStatusDto;
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
@RequestMapping("/admin/sys/accesslog")
public class AccessLogController {

    @Resource
    private AccessLogService accessLogService;

    @ApiOperation("分页查询用户访问日志")
    @RestPostMapping("/list")
    public ResponseStatusDto<PageData<AccessLogEntity>> getLogList(
            @RequestBody PageRequest<AccessLogQueryDto> pageRequest) {
        PageData<AccessLogEntity> pageData = accessLogService.getLogList(pageRequest);
        return ResponseStatusDto.success(pageData);
    }

    @ApiOperation("批量删除日志")
    @RestPostMapping("/deleteBatch/{unit}")
    public ResponseStatusDto<String> deleteBatch(@PathVariable String unit) {
        accessLogService.deleteBatch(unit);
        return ResponseStatusDto.success();
    }

}
