package com.glv.music.system.modules.rbac.controller;

import com.glv.music.system.modules.rbac.dto.RbacAuthDto;
import com.glv.music.system.modules.rbac.entity.RbacAuthEntity;
import com.glv.music.system.modules.rbac.service.RbacAuthService;
import com.glv.music.system.modules.request.annotation.RestGetMapping;
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
import java.util.HashSet;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Controller
@Api
@RequestMapping("/admin/sys/auth")
public class RbacAuthController {

    @Resource
    private RbacAuthService rbacAuthService;

    @ApiOperation("保存")
    @RestPostMapping("/save")
    public ResponseStatusDto<String> save(@RequestBody RbacAuthDto dto) {
        rbacAuthService.save(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("分页查询")
    @RestPostMapping("/list")
    public ResponseStatusDto<PageData<RbacAuthEntity>> listByPage(
            @RequestBody PageRequest<RbacAuthDto> pageRequest) {
        return ResponseStatusDto.success(
                rbacAuthService.listByPage(pageRequest)
        );
    }

    @ApiOperation("删除")
    @RestPostMapping("/deleteById")
    public ResponseStatusDto<String> deleteById(@RequestBody RbacAuthDto dto) {
        rbacAuthService.deleteById(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("批量删除")
    @RestPostMapping("/deleteBatch")
    public ResponseStatusDto<String> deleteBatch(@RequestBody List<Long> ids) {
        rbacAuthService.deleteBatch(ids);
        return ResponseStatusDto.success();
    }

    @ApiOperation("获取用户权限")
    @RestGetMapping("/getUserAuths")
    public ResponseStatusDto<HashSet<String>> getUserAuths(Long userId) {
        return ResponseStatusDto.success(
                rbacAuthService.getUserAuths(userId)
        );
    }

    @ApiOperation("判断用户是否具有权限")
    @RestGetMapping("/userHasAuth")
    public ResponseStatusDto<Boolean> userHasAuth(Long userId, String authCode) {
        return ResponseStatusDto.success(
                rbacAuthService.userHasAuth(userId, authCode)
        );
    }
}
