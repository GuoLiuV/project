package com.glv.music.system.modules.rbac.controller;

import com.glv.music.system.modules.rbac.dto.RbacRoleDto;
import com.glv.music.system.modules.rbac.entity.RbacRoleEntity;
import com.glv.music.system.modules.rbac.service.RbacRoleService;
import com.glv.music.system.modules.request.annotation.RestPostMapping;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import com.glv.music.system.modules.response.dto.ResponseStatusDto;
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
@Api
@Controller
@RequestMapping("/admin/sys/role")
public class RbacRoleController {

    @Resource
    private RbacRoleService rbacRoleService;

    @ApiOperation("角色保存")
    @RestPostMapping("/save")
    public ResponseStatusDto<String> saveRole(@RequestBody RbacRoleDto rbacRoleDto) {
        rbacRoleService.saveRole(rbacRoleDto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("分页查询角色")
    @RestPostMapping("/list")
    public ResponseStatusDto<PageData<RbacRoleEntity>> getRoleList(@RequestBody PageRequest<RbacRoleDto> pageRequest) {
        PageData<RbacRoleEntity> pageData = rbacRoleService.getRoleList(pageRequest);
        return ResponseStatusDto.success(pageData);
    }

    @ApiOperation("删除角色")
    @RestPostMapping("/delete")
    public ResponseStatusDto<String> deleteRole(@RequestBody RbacRoleDto rbacRoleDto) {
        rbacRoleService.deleteRole(rbacRoleDto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("批量删除角色")
    @RestPostMapping("/deleteBatch")
    public ResponseStatusDto<String> deleteBatchRole(@RequestBody List<Long> ids) {
        rbacRoleService.deleteBatchRole(ids);
        return ResponseStatusDto.success();
    }
}
