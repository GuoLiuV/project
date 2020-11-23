package com.glv.project.system.modules.oauth2.service;

import com.glv.project.system.modules.oauth2.dto.LoginUserInfoDto;

/**
 * @author ZHOUXIANG
 */
public interface Oauth2Service {

    /**
     * 获取当前登录用户
     * @return 登录用户
     */
    LoginUserInfoDto getLoginUserInfo();
}
