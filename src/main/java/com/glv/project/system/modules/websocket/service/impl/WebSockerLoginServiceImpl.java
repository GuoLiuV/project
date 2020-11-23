package com.glv.project.system.modules.websocket.service.impl;

import com.glv.project.system.modules.sysadmin.SysAdminUtils;
import com.glv.project.system.modules.websocket.server.LoginWebSocketServer;
import com.glv.project.system.modules.websocket.service.WebSockerLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ZHOUXIANG
 */
@Service
@Slf4j
public class WebSockerLoginServiceImpl implements WebSockerLoginService {

    @Override
    public String getTokenAndSendToClient(String sid) {
        // 获取当前用户的token
        String token = SysAdminUtils.getCurrentLoginToken();
        LoginWebSocketServer.sendMessage(token, sid);
        return token;
    }
}
