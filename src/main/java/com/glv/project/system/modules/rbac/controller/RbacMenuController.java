package com.glv.project.system.modules.rbac.controller;

import com.glv.project.system.modules.rbac.dto.RbacMenuDto;
import com.glv.project.system.modules.rbac.entity.RbacMenuEntity;
import com.glv.project.system.modules.rbac.service.RbacMenuService;
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
@RequestMapping("/admin/sys/menu")
public class RbacMenuController {

    @Resource
    private RbacMenuService rbacMenuService;

    @ApiOperation("保存")
    @RestPostMapping("/save")
    public ResponseStatusDto<String> save(@RequestBody RbacMenuDto dto) {
        rbacMenuService.save(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("分页查询")
    @RestPostMapping("/list")
    public ResponseStatusDto<PageData<RbacMenuEntity>> list(
            @RequestBody PageRequest<RbacMenuDto> pageRequest) {
        return ResponseStatusDto.success(
                rbacMenuService.list(pageRequest)
        );
    }

    @ApiOperation("删除")
    @RestPostMapping("/delete")
    public ResponseStatusDto<String> deleteById(@RequestBody RbacMenuDto dto) {
        rbacMenuService.delete(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("批量删除")
    @RestPostMapping("/deleteBatch")
    public ResponseStatusDto<String> deleteBatch(@RequestBody List<Long> ids) {
        rbacMenuService.deleteBatch(ids);
        return ResponseStatusDto.success();
    }
}
