package com.glv.music.system.modules.security.provider;

import com.glv.music.system.enums.StriveConstantsEnum;
import com.glv.music.system.modules.captcha.service.CaptchaService;
import com.glv.music.system.modules.rbac.entity.RbacUserEntity;
import com.glv.music.system.modules.rbac.service.RbacUserRoleService;
import com.glv.music.system.modules.rbac.service.RbacUserService;
import com.glv.music.system.modules.security.dto.StriveUserDetails;
import com.glv.music.system.utils.CollectionUtils;
import com.glv.music.system.utils.ObjectUtils;
import com.glv.music.system.utils.RequestUtils;
import com.glv.music.system.utils.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@SuppressWarnings("unused")
public class StriveAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private RbacUserService rbacUserService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RbacUserRoleService rbacUserRoleService;

    @Resource
    private CaptchaService captchaService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String username = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());
        if (StringUtils.isBlank(username)) {
            throw new BadCredentialsException("用户名为空");
        }
        UserDetails userDetails;
        if (isMobileLogin()) {
            userDetails = mobileUserAuthenticate(username, password);
        } else if(isWechatMobileLogin()) {
            userDetails = wechatMobileUserAuthenticate(username);
        } else {
            userDetails = userPasswordAuthenticate(username, password);
        }
        if (ObjectUtils.isNull(userDetails)) {
            throw new BadCredentialsException("认证失败");
        }
        return createSuccessAuthentication(userDetails, authentication, userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authentication));
    }

    /**
     * 判断是否是手机号和验证码登录，默认用户名密码登录
     */
    private boolean isMobileLogin() {
        return StringUtils.equalsIgnoreCase("mobile",
                RequestUtils.getRequestParameter("loginType"));
    }

    /**
     * 判断是否是微信手机号登录，默认用户名密码登录
     */
    private boolean isWechatMobileLogin() {
        return StringUtils.equalsIgnoreCase("wxMobile",
                RequestUtils.getRequestParameter("loginType"));
    }

    /**
     * 用户名密码登录方式
     */
    private UserDetails userPasswordAuthenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (ObjectUtils.isNull(userDetails) ||
                !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("用户名或密码不正确");
        }
        return userDetails;
    }

    /**
     * 手机号配合验证码登录处理
     */
    private UserDetails mobileUserAuthenticate(String mobile, String password) {
        if (!captchaService.validCaptchaCode(mobile, password)) {
            throw new BadCredentialsException("验证码不正确");
        }
        return this.wechatMobileUserAuthenticate(mobile);
    }

    /**
     * 微信手机号登录处理
     */
    private UserDetails wechatMobileUserAuthenticate(String mobile) {
        RbacUserEntity user = rbacUserService.getOneByMobileAndInsert(mobile);
        if (ObjectUtils.notNull(user)) {
            StriveUserDetails striveUserDetails = new StriveUserDetails();
            striveUserDetails.setUser(user);
            HashSet<String> roleCodeList =
                    rbacUserRoleService.getUserRoles(user.getId());
            List<GrantedAuthority> grants = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(roleCodeList)) {
                for (String roleCode : roleCodeList) {
                    grants.add(new SimpleGrantedAuthority(
                            StriveConstantsEnum.AUTH_PREFIX.value.concat(roleCode)));
                }
            } else {
                grants.add(new SimpleGrantedAuthority(
                        StriveConstantsEnum.AUTH_PREFIX.value.concat("USER")));
            }
            striveUserDetails.setAuthorities(grants);
            return striveUserDetails;
        }
        return null;
    }

    /**
     * 交给spring进行处理
     */
    private Authentication createSuccessAuthentication(Object principal,
                                                       Authentication authentication, UserDetails user) {
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                principal, authentication.getCredentials(), user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }
}
