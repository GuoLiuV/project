package com.glv.project.system.modules.rbac.controller;

import com.glv.project.system.modules.rbac.dto.RbacOrgRoleDto;
import com.glv.project.system.modules.rbac.service.RbacOrgRoleService;
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
@RequestMapping("/admin/sys/org-role")
@Api
public class RbacOrgRoleController {

    @Resource
    private RbacOrgRoleService rbacOrgRoleService;

    @ApiOperation("保存组织角色关联信息")
    @RestPostMapping("save")
    public ResponseStatusDto<String> saveOrgRoleJoin(@RequestBody RbacOrgRoleDto dto) {
        rbacOrgRoleService.save(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("查询该组织对象的角色ID列表")
    @RestGetMapping("/getIds/{orgId}")
    public ResponseStatusDto<List<Long>> getRoleIdsByOrgId(@PathVariable Long orgId) {
        return ResponseStatusDto.success(
                rbacOrgRoleService.getIds(orgId)
        );
    }
}
