package com.glv.project.system.modules.rbac.controller;

import com.glv.project.system.modules.rbac.dto.RbacRoleAuthDto;
import com.glv.project.system.modules.rbac.service.RbacRoleAuthService;
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
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Controller
@Api
@RequestMapping("/admin/sys/role-auth")
public class RbacRoleAuthController {

    @Resource
    private RbacRoleAuthService rbacRoleAuthService;

    @ApiOperation("保存角色权限关系")
    @RestPostMapping("/save")
    public ResponseStatusDto<String> save(@RequestBody RbacRoleAuthDto dto) {
        rbacRoleAuthService.save(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("获取角色权限关系")
    @RestGetMapping("/getAuthIds/{roleId}")
    public ResponseStatusDto<List<Long>> getAuthIds(@PathVariable Long roleId) {
        return ResponseStatusDto.success(
                rbacRoleAuthService.getAuthIds(roleId)
        );
    }
}
