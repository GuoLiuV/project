package com.glv.project.system.modules.scheduling.controller;

import com.glv.project.system.modules.request.annotation.RestPostMapping;
import com.glv.project.system.modules.request.annotation.RestRequestMapping;
import com.glv.project.system.modules.request.dto.PageRequest;
import com.glv.project.system.modules.response.dto.PageData;
import com.glv.project.system.modules.response.dto.ResponseStatusDto;
import com.glv.project.system.modules.scheduling.dto.StriveSchExecutorDto;
import com.glv.project.system.modules.scheduling.entity.StriveSchExecutorEntity;
import com.glv.project.system.modules.scheduling.service.StriveSchExecutorService;
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
@RequestMapping("/admin/sys/sched/executor")
@Api
public class StriveSchExecutorController {

    @Resource
    private StriveSchExecutorService striveSchExecutorService;

    @ApiOperation("保存")
    @RestPostMapping("/save")
    public ResponseStatusDto<String> save(
            @RequestBody StriveSchExecutorDto dto) {
        striveSchExecutorService.save(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("查询")
    @RestPostMapping("/list")
    public ResponseStatusDto<PageData<StriveSchExecutorEntity>> list(
            @RequestBody PageRequest<StriveSchExecutorDto> pageRequest) {
        return ResponseStatusDto.success(
                striveSchExecutorService.list(pageRequest)
        );
    }

    @ApiOperation("删除")
    @RestRequestMapping("/delete/{id}")
    public ResponseStatusDto<String> delete(@PathVariable Long id) {
        striveSchExecutorService.deleteById(id);
        return ResponseStatusDto.success();
    }
}
