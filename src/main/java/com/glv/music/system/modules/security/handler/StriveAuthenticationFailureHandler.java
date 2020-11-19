package com.glv.music.system.modules.security.handler;

import com.glv.music.system.modules.response.dto.ResponseStatusDto;
import com.glv.music.system.utils.AjaxUtils;
import com.glv.music.system.utils.JSONUtils;
import com.glv.music.system.utils.ResponseUtils;
import lombok.Data;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ZHOUXIANG
 */
@Data
public class StriveAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /**
     * 定义失败类型,captcha为验证码失败,否则其它失败
     */
    private String type;

    private final String flag = "captcha";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        if (AjaxUtils.isAjaxRequest(request)) {
            ResponseStatusDto<String> responseStatusDto = ResponseStatusDto
                    .notAuthentication("用户名或密码不正确");
            if (flag.equals(type)) {
                responseStatusDto.setMessage("验证码不正确");
            }
            ResponseUtils.writeJson(response, JSONUtils.obj2Json(responseStatusDto));
        } else {
            if (flag.equals(type)) {
                response.sendRedirect(request.getContextPath() + "/login_page?captcha");
            } else {
                response.sendRedirect(request.getContextPath() + "/login_page?error");
            }
        }

    }
}
