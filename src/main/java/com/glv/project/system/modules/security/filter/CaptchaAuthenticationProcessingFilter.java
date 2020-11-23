package com.glv.project.system.modules.security.filter;

import com.glv.project.system.modules.security.handler.StriveAuthenticationFailureHandler;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ZHOUXIANG
 */
@SuppressWarnings("unused")
public class CaptchaAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public CaptchaAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/loginProcess", "POST"));
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        StriveAuthenticationFailureHandler failureHandler =
                (StriveAuthenticationFailureHandler) this.getFailureHandler();

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        //非认证阶段请求拦截
        if (!requiresAuthentication(request, response)) {
            chain.doFilter(request, response);
            return;
        }

        String captcha = request.getParameter("captcha");
        String captchaId = (String) request.getSession().getAttribute("captcha");
        if (captcha == null || !captcha.equalsIgnoreCase(captchaId)) {
            //向failureHandler报告验证码错误
            failureHandler.setType("captcha");
            this.unsuccessfulAuthentication(request, response, new InsufficientAuthenticationException("验证码不正确"));
        } else {
            failureHandler.setType(null);
            chain.doFilter(request, response);
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        return null;
    }

}
