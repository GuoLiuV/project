package com.glv.project.system.modules.sysadmin.controller;

import com.glv.project.system.modules.rbac.entity.RbacOrgEntity;
import com.glv.project.system.modules.rbac.entity.RbacUserEntity;
import com.glv.project.system.modules.request.annotation.RestGetMapping;
import com.glv.project.system.modules.response.dto.ResponseStatusDto;
import com.glv.project.system.modules.sysadmin.service.SysAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashSet;

/**
 * @author ZHOUXIANG
 */
@Controller
@RequestMapping("/admin/sys/sys_admin")
@Api
public class SysAdminController {

    @Resource
    private SysAdminService sysAdminService;

    @ApiOperation("获取登录名")
    @RestGetMapping("/getCurrentUserLoginName")
    public ResponseStatusDto<String> getCurrentUserLoginName() {
        return sysAdminService.getCurrentUserLoginName();
    }

    @ApiOperation("获取登录用户")
    @RestGetMapping("/getCurrentUserEntity")
    public ResponseStatusDto<RbacUserEntity> getCurrentUserEntity() {
        return sysAdminService.getCurrentUserEntity();
    }

    @ApiOperation("获取登录用户租户ID")
    @RestGetMapping("/getCurrentTenantId")
    public ResponseStatusDto<Long> getCurrentTenantId() {
        return sysAdminService.getCurrentTenantId();
    }

    @ApiOperation("获取登录用户角色")
    @RestGetMapping("/getUserRoles")
    public ResponseStatusDto<HashSet<String>> getUserRoles() {
        return sysAdminService.getUserRoles();
    }

    @ApiOperation("判断登录用户是否具有该角色")
    @RestGetMapping("/userHasRole")
    public ResponseStatusDto<Boolean> userHasRole(String roleCode) {
        return sysAdminService.userHasRole(roleCode);
    }

    @ApiOperation("获取登录用户权限")
    @RestGetMapping("/getUserAuths")
    public ResponseStatusDto<HashSet<String>> getUserAuths() {
        return sysAdminService.getUserAuths();
    }

    @ApiOperation("判断登录用户是否具有权限")
    @RestGetMapping("/userHasAuth")
    public ResponseStatusDto<Boolean> userHasAuth(String authCode) {
        return sysAdminService.userHasAuth(authCode);
    }

    @ApiOperation("获取登录用户资源")
    @RestGetMapping("/getUseRes")
    public ResponseStatusDto<HashSet<String>> getUseRes(Long userId, String authType) {
        return sysAdminService.getUserRes(userId, authType);
    }

    @ApiOperation("判断登录用户是否具有该资源")
    @RestGetMapping("/userHasRes")
    public ResponseStatusDto<Boolean> userHasRes(Long userId, String resCode) {
        return sysAdminService.userHasRes(userId, resCode);
    }

    @ApiOperation("获取当前用户组织")
    @RestGetMapping("/getUserOrg")
    public ResponseStatusDto<RbacOrgEntity> getUserOrg(Long userId) {
        return sysAdminService.getUserOrg(userId);
    }
}
