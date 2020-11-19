package com.glv.music.system.modules.rbac.controller;

import com.glv.music.system.modules.rbac.dto.RbacUserRegDto;
import com.glv.music.system.modules.rbac.service.RbacUserService;
import com.glv.music.system.modules.request.annotation.RestPostMapping;
import com.glv.music.system.modules.response.dto.ResponseStatusDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author ZHOUXIANG
 */
@Controller
@RequestMapping("/sys/user")
@Api("租户注册")
public class UserRegisterController {

    @Resource
    private RbacUserService rbacUserService;

    @ApiOperation("租户注册接口")
    @RestPostMapping("/register")
    public ResponseStatusDto<String> registerUser(
            @RequestBody RbacUserRegDto regDto) {
        rbacUserService.registerUser(regDto);
        return ResponseStatusDto.success();
    }
}
