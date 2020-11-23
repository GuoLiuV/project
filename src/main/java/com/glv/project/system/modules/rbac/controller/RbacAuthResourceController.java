package com.glv.project.system.modules.rbac.controller;

import com.glv.project.system.modules.rbac.dto.RbacAuthResourceDto;
import com.glv.project.system.modules.rbac.service.RbacAuthResourceService;
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
@Api
@RequestMapping("/admin/sys/auth-resource")
public class RbacAuthResourceController {

    @Resource
    private RbacAuthResourceService rbacAuthResourceService;

    @ApiOperation("保存权限资源关系")
    @RestPostMapping("/save")
    public ResponseStatusDto<String> save(@RequestBody RbacAuthResourceDto dto) {
        rbacAuthResourceService.save(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("获取权限资源关系")
    @RestGetMapping("/getResIds/{authId}")
    public ResponseStatusDto<List<Long>> getAuthIds(@PathVariable Long authId) {
        return ResponseStatusDto.success(
                rbacAuthResourceService.getIds(authId)
        );
    }

    @ApiOperation("获取用户各种资源集合")
    @RestGetMapping("/getUserRes")
    public ResponseStatusDto<HashSet<String>> getUserRes(Long userId, String authType) {
        return ResponseStatusDto.success(
                rbacAuthResourceService.getUserRes(userId, authType)
        );
    }

    @ApiOperation("判断用户是否具有该资源")
    @RestGetMapping("/userHasRes")
    public ResponseStatusDto<Boolean> userHasRes(Long userId, String resCode) {
        return ResponseStatusDto.success(
                rbacAuthResourceService.userHasRes(userId, resCode)
        );
    }
}
