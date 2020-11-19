package com.glv.music.system.modules.oauth2.controller;

import com.glv.music.system.modules.oauth2.dto.LoginUserInfoDto;
import com.glv.music.system.modules.oauth2.service.Oauth2Service;
import com.glv.music.system.modules.oauth2.service.StriveClientDetailsService;
import com.glv.music.system.modules.request.annotation.RestGetMapping;
import com.glv.music.system.modules.response.dto.ResponseStatusDto;
import com.glv.music.system.modules.sysadmin.SysAdminUtils;
import com.glv.music.system.utils.AjaxUtils;
import com.glv.music.system.utils.JSONUtils;
import com.glv.music.system.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ZHOUXIANG
 */
@Controller
@Api
public class Oauth2Controller {

    @Resource
    private StriveClientDetailsService striveClientDetailsService;

    @Resource
    private Oauth2Service oauth2Service;

    /**
     * 获取登录用户的详细信息
     */
    @RestGetMapping("/admin/sys/user_info")
    @ApiOperation("获取当前登录用户信息")
    public ResponseStatusDto<LoginUserInfoDto> getCurrentUserInfo() {
        return ResponseStatusDto.success(
                oauth2Service.getLoginUserInfo()
        );
    }

    /**
     * 获取当前登录的用户名
     */
    @RestGetMapping("/admin/sys/user_name")
    @ApiOperation("获取当前登录用户登录名")
    public ResponseStatusDto<String> getCurrentUserName() {
        String userName = SysAdminUtils.getCurrentUserLoginName();
        return ResponseStatusDto.success(userName);
    }

    /**
     * 由于Oauth2 Token认证方式并没有提供注销功能，
     * 并且注销功能需要session，因此单独实现删除token功能
     */
    @RequestMapping(value = {"/logout_page", "/admin/sys/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation("注销当前用户")
    public void oauth2Logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        striveClientDetailsService.oauth2Logout();
        if (AjaxUtils.isAjaxRequest(request)) {
            ResponseStatusDto statusDto = ResponseStatusDto.success("注销成功");
            ResponseUtils.writeJson(response, JSONUtils.obj2Json(statusDto));
        } else {
            response.addCookie(new Cookie("access_token", null));
            response.sendRedirect(request.getContextPath().concat("/index"));
        }
    }
}
