package com.glv.project.system.modules.rbac.controller;

import com.glv.project.system.modules.rbac.dto.RbacElemDto;
import com.glv.project.system.modules.rbac.entity.RbacElemEntity;
import com.glv.project.system.modules.rbac.service.RbacElemService;
import com.glv.project.system.modules.request.annotation.RestPostMapping;
import com.glv.project.system.modules.request.dto.PageRequest;
import com.glv.project.system.modules.response.dto.PageData;
import com.glv.project.system.modules.response.dto.ResponseStatusDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Controller
@Api
@RequestMapping("/admin/sys/elem")
public class RbacElemController {

    @Resource
    private RbacElemService rbacElemService;

    @ApiOperation("保存")
    @RestPostMapping("/save")
    public ResponseStatusDto<String> save(@RequestBody RbacElemDto dto) {
        rbacElemService.save(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("分页查询")
    @RestPostMapping("/list")
    public ResponseStatusDto<PageData<RbacElemEntity>> list(
            @RequestBody PageRequest<RbacElemDto> pageRequest) {
        return ResponseStatusDto.success(
                rbacElemService.list(pageRequest)
        );
    }

    @ApiOperation("删除")
    @RestPostMapping("/delete")
    public ResponseStatusDto<String> deleteById(@RequestBody RbacElemDto dto) {
        rbacElemService.delete(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("批量删除")
    @RestPostMapping("/deleteBatch")
    public ResponseStatusDto<String> deleteBatch(@RequestBody List<Long> ids) {
        rbacElemService.deleteBatch(ids);
        return ResponseStatusDto.success();
    }
}
