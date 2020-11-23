package com.glv.project.system.modules.security.handler;

import com.glv.project.system.modules.response.dto.ResponseStatusDto;
import com.glv.project.system.utils.AjaxUtils;
import com.glv.project.system.utils.JSONUtils;
import com.glv.project.system.utils.ResponseUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ZHOUXIANG
 */
@SuppressWarnings("unused")
public class StriveAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        if (AjaxUtils.isAjaxRequest(request)) {
            ResponseStatusDto<String> responseStatusDto = ResponseStatusDto.success("登录成功");
            ResponseUtils.writeJson(response, JSONUtils.obj2Json(responseStatusDto));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

}
