package com.glv.project.system.modules.oauth2.service.impl;

import com.glv.project.system.enums.StriveConstantsEnum;
import com.glv.project.system.modules.oauth2.client.ClientDetailsImpl;
import com.glv.project.system.modules.oauth2.entity.ClientDetailsEntity;
import com.glv.project.system.modules.oauth2.service.StriveClientDetailsService;
import com.glv.project.system.utils.JSONUtils;
import com.glv.project.system.utils.ObjectUtils;
import com.glv.project.system.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 自定义Oauth2认证客户端服务
 *
 * @author ZHOUXIANG
 */
@Slf4j
@Service
@Primary
@SuppressWarnings("unused")
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Resource
    private StriveClientDetailsService striveClientDetailsService;

    @Override
    @Cacheable(value = "sys", key = "#root.methodName + '_' + #clientId")
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetailsEntity client = striveClientDetailsService.getClientDetailsByClientId(clientId);
        if (ObjectUtils.isNull(client)) {
            if (StringUtils.equalsIgnoreCase(clientId, StriveConstantsEnum.STRIVE_APP_ID.value)) {
                client = new ClientDetailsEntity();
                client.setClientId(clientId)
                        .setScopes("request")
                        .setAuthorizedGrantTypes("password")
                        .setClientSecret("6d930500293d11658b1727951c5a9489")
                        .setAccessTokenValiditySeconds(3600 * 24 * 365)
                        .setRefreshTokenValiditySeconds(60);
            } else {
                log.error("Oauth2认证未能根据客户端ID找到此客户端信息：{}", clientId);
            }
        }
        if (ObjectUtils.notNull(client)) {
            ClientDetailsImpl clientDetails = new ClientDetailsImpl(client);
            log.debug("Oauth2请求的客户端信息为：{}", JSONUtils.obj2Json(clientDetails));
            return clientDetails;
        }
        return null;
    }

}
