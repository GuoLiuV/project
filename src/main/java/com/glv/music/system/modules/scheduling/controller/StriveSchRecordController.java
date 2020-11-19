package com.glv.music.system.modules.scheduling.controller;

import com.glv.music.system.modules.request.annotation.RestPostMapping;
import com.glv.music.system.modules.request.annotation.RestRequestMapping;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import com.glv.music.system.modules.response.dto.ResponseStatusDto;
import com.glv.music.system.modules.scheduling.dto.StriveSchRecordDto;
import com.glv.music.system.modules.scheduling.entity.StriveSchRecordEntity;
import com.glv.music.system.modules.scheduling.service.StriveSchRecordService;
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
@RequestMapping("/admin/sys/sched/record")
@Api
public class StriveSchRecordController {

    @Resource
    private StriveSchRecordService striveSchRecordService;

    @ApiOperation("保存")
    @RestPostMapping("/save")
    public ResponseStatusDto<String> save(
            @RequestBody StriveSchRecordDto dto) {
        striveSchRecordService.save(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("查询")
    @RestPostMapping("/list")
    public ResponseStatusDto<PageData<StriveSchRecordEntity>> list(
            @RequestBody PageRequest<StriveSchRecordDto> pageRequest) {
        return ResponseStatusDto.success(
                striveSchRecordService.list(pageRequest)
        );
    }

    @ApiOperation("清除日志")
    @RestRequestMapping("/deleteBatch/{unit}")
    public ResponseStatusDto<String> delete(@PathVariable String unit) {
        striveSchRecordService.deleteBatch(unit);
        return ResponseStatusDto.success();
    }
}
