package com.glv.project.system.modules.rbac.controller;

import com.glv.project.system.modules.rbac.dto.RbacResDto;
import com.glv.project.system.modules.rbac.entity.RbacResEntity;
import com.glv.project.system.modules.rbac.service.RbacResService;
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
@RequestMapping("/admin/sys/res")
public class RbacResController {

    @Resource
    private RbacResService rbacResService;

    @ApiOperation("保存")
    @RestPostMapping("/save")
    public ResponseStatusDto<String> save(@RequestBody RbacResDto dto) {
        rbacResService.save(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("分页查询")
    @RestPostMapping("/list")
    public ResponseStatusDto<PageData<RbacResEntity>> list(
            @RequestBody PageRequest<RbacResDto> pageRequest) {
        return ResponseStatusDto.success(
                rbacResService.list(pageRequest)
        );
    }

    @ApiOperation("删除")
    @RestPostMapping("/delete")
    public ResponseStatusDto<String> deleteById(@RequestBody RbacResDto dto) {
        rbacResService.delete(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("批量删除")
    @RestPostMapping("/deleteBatch")
    public ResponseStatusDto<String> deleteBatch(@RequestBody List<Long> ids) {
        rbacResService.deleteBatch(ids);
        return ResponseStatusDto.success();
    }
}
