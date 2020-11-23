package com.glv.project.system.modules.oauth2.config;

import com.glv.project.system.modules.property.StriveProperties;
import com.glv.project.system.modules.web.component.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;

/**
 * 开启oauth2的认证服务
 *
 * @author ZHOUXIANG
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
@SuppressWarnings("unused")
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Resource
    private StriveProperties striveProperties;

    @Resource
    private OAuth2ProtectedResourceDetails resource;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(SpringContextHolder.getBean(ClientDetailsService.class));
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(SpringContextHolder.getBean(PasswordEncoder.class));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // password模式必需要authenticationManager
        endpoints.authenticationManager(SpringContextHolder.getBean(AuthenticationManager.class))
                .accessTokenConverter(new DefaultAccessTokenConverter())
                .userDetailsService(SpringContextHolder.getBean(UserDetailsService.class))
                .tokenStore(tokenStore())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Bean
    public TokenStore tokenStore() {
        if (striveProperties.isRedisTokenEnabled()) {
            return new RedisTokenStore(redisConnectionFactory);
        } else {
            return new InMemoryTokenStore();
        }
    }

    @Bean
    @Qualifier("resourcePassword")
    public OAuth2ProtectedResourceDetails resourcePassword() {
        ResourceOwnerPasswordResourceDetails resourcePassword =
                new ResourceOwnerPasswordResourceDetails();
        resourcePassword.setId(resource.getId());
        resourcePassword.setClientId(resource.getClientId());
        resourcePassword.setClientSecret(resource.getClientSecret());
        resourcePassword.setScope(resource.getScope());
        resourcePassword.setAccessTokenUri(resource.getAccessTokenUri());
        resourcePassword.setTokenName(resource.getTokenName());
        resourcePassword.setAuthenticationScheme(resource.getAuthenticationScheme());
        resourcePassword.setClientAuthenticationScheme(resource.getClientAuthenticationScheme());
        resourcePassword.setUsername("admin");
        resourcePassword.setPassword("123456");
        return resourcePassword;
    }

    @Bean
    @Primary
    public OAuth2ClientContext singletonClientContext() {
        return new DefaultOAuth2ClientContext();
    }

}
