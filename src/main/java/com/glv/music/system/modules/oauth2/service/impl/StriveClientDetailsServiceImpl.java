package com.glv.music.system.modules.oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.music.system.enums.StriveConstantsEnum;
import com.glv.music.system.modules.exception.StriveException;
import com.glv.music.system.modules.mybatisplus.utils.MyBatisPlusUtils;
import com.glv.music.system.modules.oauth2.dao.StriveClientDetailsMapper;
import com.glv.music.system.modules.oauth2.dto.ClientDetailsDto;
import com.glv.music.system.modules.oauth2.entity.ClientDetailsEntity;
import com.glv.music.system.modules.oauth2.service.StriveClientDetailsService;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import com.glv.music.system.modules.web.component.SpringContextHolder;
import com.glv.music.system.utils.*;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

/**
 * Oauth2客户端信息服务类
 *
 * @author ZHOUXIANG
 */
@Service
public class StriveClientDetailsServiceImpl
        extends ServiceImpl<StriveClientDetailsMapper, ClientDetailsEntity>
        implements StriveClientDetailsService {

    @Override
    public ClientDetailsEntity getClientDetailsByClientId(String clientId) {
        QueryWrapper<ClientDetailsEntity> queryWrapper =
                new QueryWrapper<ClientDetailsEntity>().eq("client_id", clientId);
        return this.baseMapper.getClientByClientId(queryWrapper);
    }

    @Override
    public void saveClientDetails(ClientDetailsEntity entity) {
        if (!this.saveOrUpdate(entity)) {
            throw new StriveException("保存或更新Oauth2客户端信息失败");
        }
    }

    @Override
    public void saveClientDetails(@Valid ClientDetailsDto dto) {
        // 添加的客户端ID不能与系统内置的客户端ID相同
        if (StringUtils.equalsIgnoreCase(dto.getClientId(),
                StriveConstantsEnum.STRIVE_APP_ID.value)) {
            throw new StriveException("该appId已存在");
        }
        // 保证只有在secret字段有值时更新，否则将secret置null，忽略更新
        if (StringUtils.isNotBlank(dto.getClientSecret())) {
            String digest = DigestUtils.md5Hex(dto.getClientSecret());
            dto.setClientSecret(digest);
        } else {
            dto.setClientSecret(null);
        }
        ClientDetailsEntity entity = new ClientDetailsEntity();
        BeanUtils.copyPropertiesIgnoreNull(dto, entity);
        try {
            if (!this.saveOrUpdate(entity)) {
                throw new StriveException("客户端信息保存失败");
            }
        }catch (DuplicateKeyException e) {
            throw new StriveException("该appId已存在");
        }
    }

    @Override
    public PageData<ClientDetailsEntity> getClientDetailsList(PageRequest<ClientDetailsDto> pageRequest) {
        QueryWrapper<ClientDetailsEntity> queryWrapper = new QueryWrapper<>();
        MyBatisPlusUtils.buildQuery(queryWrapper, pageRequest.getCondition());
        Page<ClientDetailsEntity> page = pageRequest.buildMybatisPlusPage();
        IPage<ClientDetailsEntity> iPage = this.page(page, queryWrapper);
        return new PageData<>(iPage);
    }

    @Override
    public void deleteClientDetails(ClientDetailsDto dto) {
        if (ObjectUtils.isNull(dto.getId())) {
            throw new StriveException("要删除的ID不存在");
        }
        this.removeById(dto.getId());
    }

    @Override
    public void deleteBatchClientDetails(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new StriveException("要删除的ID不存在");
        }
        this.removeByIds(ids);
    }

    @Override
    public void oauth2Logout() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (ObjectUtils.notNull(auth)) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)auth.getDetails();
            String token = details.getTokenValue();
            if (StringUtils.isNotBlank(token)) {
                TokenStore tokenStore = SpringContextHolder.getBean(TokenStore.class);
                OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
                if (ObjectUtils.notNull(oAuth2AccessToken)) {
                    tokenStore.removeAccessToken(oAuth2AccessToken);
                }
            }
        }
    }
}
