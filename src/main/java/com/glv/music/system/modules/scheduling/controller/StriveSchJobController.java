package com.glv.music.system.modules.scheduling.controller;

import com.glv.music.system.modules.request.annotation.RestPostMapping;
import com.glv.music.system.modules.request.annotation.RestRequestMapping;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import com.glv.music.system.modules.response.dto.ResponseStatusDto;
import com.glv.music.system.modules.scheduling.dto.StriveSchJobDto;
import com.glv.music.system.modules.scheduling.entity.StriveSchJobEntity;
import com.glv.music.system.modules.scheduling.service.StriveSchJobService;
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
@RequestMapping("/admin/sys/sched/job")
@Api
public class StriveSchJobController {

    @Resource
    private StriveSchJobService striveSchJobService;

    @ApiOperation("保存")
    @RestPostMapping("/save")
    public ResponseStatusDto<String> save(
            @RequestBody StriveSchJobDto dto) {
        striveSchJobService.save(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("查询")
    @RestPostMapping("/list")
    public ResponseStatusDto<PageData<StriveSchJobEntity>> list(
            @RequestBody PageRequest<StriveSchJobDto> pageRequest) {
        return ResponseStatusDto.success(
                striveSchJobService.list(pageRequest)
        );
    }

    @ApiOperation("删除")
    @RestRequestMapping("/delete/{id}")
    public ResponseStatusDto<String> delete(@PathVariable Long id) {
        striveSchJobService.deleteJob(id);
        return ResponseStatusDto.success();
    }

    @ApiOperation("启动")
    @RestRequestMapping("/start/{id}")
    public ResponseStatusDto<String> start(@PathVariable Long id) {
        striveSchJobService.startJob(id);
        return ResponseStatusDto.success();
    }

    @ApiOperation("停止")
    @RestRequestMapping("/stop/{id}")
    public ResponseStatusDto<String> stop(@PathVariable Long id) {
        striveSchJobService.stopJob(id);
        return ResponseStatusDto.success();
    }

    @ApiOperation("执行1次")
    @RestRequestMapping("/once/{id}")
    public ResponseStatusDto<String> once(@PathVariable Long id) {
        striveSchJobService.runJobOnce(id);
        return ResponseStatusDto.success();
    }
}
