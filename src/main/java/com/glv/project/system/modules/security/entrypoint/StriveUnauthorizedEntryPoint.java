package com.glv.project.system.modules.security.entrypoint;

import com.glv.project.system.modules.response.dto.ResponseStatusDto;
import com.glv.project.system.utils.AjaxUtils;
import com.glv.project.system.utils.JSONUtils;
import com.glv.project.system.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ZHOUXIANG
 */
@Slf4j
public class StriveUnauthorizedEntryPoint extends LoginUrlAuthenticationEntryPoint {

    /**
     * @param loginFormUrl URL where the login page can be found. Should either be
     *                     relative to the web-app context path (include a leading {@code /}) or an absolute
     *                     URL.
     */
    public StriveUnauthorizedEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        if (AjaxUtils.isAjaxRequest(request)) {
            ResponseStatusDto<String> responseStatusDto = ResponseStatusDto
                    .notAuthentication("您尚未登录，请登录后再操作");
            ResponseUtils.writeJson(response, JSONUtils.obj2Json(responseStatusDto));
        } else {
            this.setUseForward(true);
            super.commence(request, response, authException);
        }

    }

}
