package com.glv.project.system.modules.drools.controller;

import com.glv.project.system.modules.drools.dto.DroolsRuleDto;
import com.glv.project.system.modules.drools.dto.DroolsRuleExecDto;
import com.glv.project.system.modules.drools.entity.DroolsRuleEntity;
import com.glv.project.system.modules.drools.service.DroolsRuleService;
import com.glv.project.system.modules.request.annotation.RestPostMapping;
import com.glv.project.system.modules.request.annotation.RestRequestMapping;
import com.glv.project.system.modules.request.dto.PageRequest;
import com.glv.project.system.modules.response.dto.PageData;
import com.glv.project.system.modules.response.dto.ResponseStatusDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author ZHOUXIANG
 */
@Controller
@RequestMapping("/admin/sys/drools")
@Api
public class DroolsRuleController {

    @Resource
    private DroolsRuleService droolsRuleService;

    @ApiOperation("保存drl规则")
    @RestPostMapping("/drl/save")
    public ResponseStatusDto<PageData<DroolsRuleEntity>> save(
            @RequestBody DroolsRuleDto dto) {
        droolsRuleService.save(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("上传解析drl文本内容")
    @RestRequestMapping("/drl/upload")
    public ResponseStatusDto<String> resolveDrlFile(MultipartFile file) {
        return ResponseStatusDto.success(
                droolsRuleService.resolveDrlFile(file)
        );
    }

    @ApiOperation("列出drl规则")
    @RestPostMapping("/drl/list")
    public ResponseStatusDto<PageData<DroolsRuleEntity>> list(
            @RequestBody PageRequest<DroolsRuleDto> pageRequest) {
        return ResponseStatusDto.success(
                droolsRuleService.list(pageRequest)
        );
    }

    @ApiOperation("删除drl规则")
    @RestPostMapping("/drl/delete")
    public ResponseStatusDto<String> delete(
            @RequestBody DroolsRuleDto dto) {
        droolsRuleService.removeById(dto.getId());
        return ResponseStatusDto.success();
    }

    @ApiOperation("应用drl规则")
    @RestPostMapping("/drl/apply")
    public ResponseStatusDto<String> applyRule(
            @RequestBody DroolsRuleDto dto) {
        droolsRuleService.applyRule(dto.getId());
        return ResponseStatusDto.success();
    }

    @ApiOperation("执行drl规则")
    @RestPostMapping("/drl/execute")
    public ResponseStatusDto<DroolsRuleExecDto> executeRule(
            @RequestBody DroolsRuleExecDto dto) {
        return ResponseStatusDto.success(
                droolsRuleService.executeRule(dto)
        );
    }
}
