package com.glv.project.system.modules.rbac.controller;

import com.glv.project.system.modules.rbac.dto.RbacUserRoleDto;
import com.glv.project.system.modules.rbac.service.RbacUserRoleService;
import com.glv.project.system.modules.request.annotation.RestGetMapping;
import com.glv.project.system.modules.request.annotation.RestPostMapping;
import com.glv.project.system.modules.response.dto.ResponseStatusDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Controller
@RequestMapping("/admin/sys/user-role")
@Api
public class RbacUserRoleController {

    @Resource
    private RbacUserRoleService rbacUserRoleService;

    @ApiOperation("保存用户角色关联关系")
    @RestPostMapping("/save")
    public ResponseStatusDto<String> save(@RequestBody RbacUserRoleDto dto) {
        rbacUserRoleService.save(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("查询该用户对象的角色ID列表")
    @RestGetMapping("/getIds/{userId}")
    public ResponseStatusDto<List<Long>> getIds(@PathVariable Long userId) {
        return ResponseStatusDto.success(
                rbacUserRoleService.getIds(userId)
        );
    }

    @ApiOperation("查询用户角色代码列表")
    @RestGetMapping("/getUserRoles")
    public ResponseStatusDto<HashSet<String>> getUserRoles(Long userId) {
        return ResponseStatusDto.success(
                rbacUserRoleService.getUserRoles(userId)
        );
    }

    @ApiOperation("查询用户是否拥有该角色")
    @RestGetMapping("/userHasRole")
    public ResponseStatusDto<Boolean> userHasRole(Long userId, String roleCode) {
        return ResponseStatusDto.success(
                rbacUserRoleService.userHasRole(userId, roleCode)
        );
    }
}
