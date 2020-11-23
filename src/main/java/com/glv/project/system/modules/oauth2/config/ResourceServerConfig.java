package com.glv.project.system.modules.oauth2.config;

import com.glv.project.system.enums.StriveConstantsEnum;
import com.glv.project.system.modules.oauth2.filter.ResourceAccessProcessingFilter;
import com.glv.project.system.modules.security.entrypoint.StriveUnauthorizedEntryPoint;
import com.glv.project.system.modules.security.handler.StriveAccessDeniedHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

import javax.annotation.Resource;

/**
 * @author ZHOUXIANG
 */
@Configuration
@EnableResourceServer
@SuppressWarnings("unused")
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "STRIVE_RESOURCE";

    @Resource
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(true);
        resources.tokenStore(tokenStore)
                .authenticationEntryPoint(new StriveUnauthorizedEntryPoint("/login_page"))
                .accessDeniedHandler(new StriveAccessDeniedHandler())
                .expressionHandler(expressionHandler());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new ResourceAccessProcessingFilter(), SwitchUserFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**")
                .authenticated()
                .anyRequest()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new StriveAccessDeniedHandler())
                .authenticationEntryPoint(new StriveUnauthorizedEntryPoint("/login_page"));

        http.logout().and().cors().and().csrf().disable();
    }

    private OAuth2WebSecurityExpressionHandler expressionHandler() {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setDefaultRolePrefix(StriveConstantsEnum.AUTH_PREFIX.value);
        return expressionHandler;
    }
}
