package com.glv.project.system.modules.websocket.controller;

import com.glv.project.system.modules.request.annotation.RestPostMapping;
import com.glv.project.system.modules.response.dto.ResponseStatusDto;
import com.glv.project.system.modules.websocket.service.WebSockerLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author ZHOUXIANG
 */
@Controller
@RequestMapping("/admin/sys/websocket")
@Api
public class WebSocketController {

    @Resource
    private WebSockerLoginService webSockerLoginService;

    @ApiOperation("移动端请求token并通过websocket推送到PC端")
    @RestPostMapping("/getTokenAndSendToClient/{sid}")
    public ResponseStatusDto<String> getTokenAndSendToClient(
            @PathVariable String sid) {
        return ResponseStatusDto.success(
                webSockerLoginService.getTokenAndSendToClient(sid)
        );
    }
}
