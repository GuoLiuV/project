package com.glv.project.system.modules.oauth2.client;

import com.glv.project.system.modules.oauth2.entity.ClientDetailsEntity;
import com.glv.project.system.utils.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

/**
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("unused")
public class ClientDetailsImpl extends BaseClientDetails {

    private ClientDetailsEntity entity;

    public ClientDetailsImpl(){}

    public ClientDetailsImpl(ClientDetailsEntity entity){
        super(entity.getClientId(), entity.getResourceIds(),
                entity.getScopes(), entity.getAuthorizedGrantTypes(),
                entity.getAuthorities(), entity.getRegisteredRedirectUris());
        this.entity = entity;
        super.setClientSecret(entity.getClientSecret());
        super.setAccessTokenValiditySeconds(entity.getAccessTokenValiditySeconds());
        super.setRefreshTokenValiditySeconds(entity.getRefreshTokenValiditySeconds());
        super.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(entity.getAutoApproveScopes()));
    }

}
