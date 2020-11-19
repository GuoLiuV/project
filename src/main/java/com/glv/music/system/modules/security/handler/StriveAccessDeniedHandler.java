package com.glv.music.system.modules.security.handler;

import com.glv.music.system.modules.response.dto.ResponseStatusDto;
import com.glv.music.system.utils.AjaxUtils;
import com.glv.music.system.utils.JSONUtils;
import com.glv.music.system.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ZHOUXIANG
 */
@Slf4j
public class StriveAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        if (AjaxUtils.isAjaxRequest(request)) {
            ResponseStatusDto<String> responseStatusDto =
                    ResponseStatusDto.notAuthority("您无权访问");
            ResponseUtils.writeJson(response, JSONUtils.obj2Json(responseStatusDto));
        } else {
            response.sendRedirect(request.getContextPath() + "/login_page?denied");
        }
    }

}
