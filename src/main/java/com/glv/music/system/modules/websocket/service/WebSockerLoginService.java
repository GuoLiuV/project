package com.glv.music.system.modules.websocket.service;

import org.springframework.validation.annotation.Validated;

/**
 * websocket 扫码登录功能
 *
 * @author ZHOUXIANG
 */
@Validated
public interface WebSockerLoginService {

    /**
     * 获取当前用户的token并推送到PC端
     *
     * @param sid websockt客户端ID
     * @return token
     */
    String getTokenAndSendToClient(String sid);
}
