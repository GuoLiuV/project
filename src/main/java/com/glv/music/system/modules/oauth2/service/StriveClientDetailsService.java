package com.glv.music.system.modules.oauth2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.music.system.modules.oauth2.dto.ClientDetailsDto;
import com.glv.music.system.modules.oauth2.entity.ClientDetailsEntity;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@SuppressWarnings("unused")
@Validated
public interface StriveClientDetailsService extends IService<ClientDetailsEntity> {

    /**
     * 根据客户端id获取客户端认证信息
     * @param clientId 客户端ID
     * @return 客户端信息
     */
    ClientDetailsEntity getClientDetailsByClientId(String clientId);

    /**
     * 保存客户端认证信息
     * @param entity 客户端信息实体
     */
    void saveClientDetails(@Valid ClientDetailsEntity entity);

    /**
     * 保存客户端认证信息
     * @param dto 客户端信息接口模型
     */
    void saveClientDetails(@Valid ClientDetailsDto dto);

    /**
     * 分页查询客户端信息
     * @param pageRequest 分页请求接口模型
     * @return 分页数据
     */
    PageData<ClientDetailsEntity> getClientDetailsList(PageRequest<ClientDetailsDto> pageRequest);

    /**
     * 删除客户端信息
     * @param dto 接口模型
     */
    void deleteClientDetails(ClientDetailsDto dto);

    /**
     * 批量删除客户端信息
     * @param ids 要删除的id列表
     */
    void deleteBatchClientDetails(List<Long> ids);

    /**
     * 退出登录
     */
    void oauth2Logout();
}
