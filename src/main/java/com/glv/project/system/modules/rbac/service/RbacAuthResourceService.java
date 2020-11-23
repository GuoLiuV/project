package com.glv.project.system.modules.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.project.system.modules.rbac.dto.RbacAuthResourceDto;
import com.glv.project.system.modules.rbac.entity.RbacAuthResourceEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface RbacAuthResourceService extends IService<RbacAuthResourceEntity> {

    /**
     * 保存关联信息
     *
     * @param dto 关联信息
     */
    void save(@Valid RbacAuthResourceDto dto);

    /**
     * 删除关联信息
     *
     * @param dto 关联信息
     */
    void delete(@Valid RbacAuthResourceDto dto);

    /**
     * 获取权限关联信息ID列表
     *
     * @param authId 权限ID
     * @return ID列表
     */
    List<Long> getIds(Long authId);

    /**
     * 获取相关的资源
     *
     * @param auths    权限集合
     * @param authType 权限类型
     * @return 资源代码集合
     */
    HashSet<String> getUserAuthResource(HashSet<String> auths, String authType);

    /**
     * 获取权限对应的资源
     *
     * @param authCode 权限代码
     * @param authType 权限类型
     * @return 资源集合
     */
    HashSet<String> getAuthResource(String authCode, String authType);

    /**
     * 获取户所有的各种资源
     *
     * @param userId   用户ID
     * @param authType auth type
     * @return 资源集合
     */
    HashSet<String> getUserRes(Long userId, String authType);

    /**
     * 用户是否具有该资源
     *
     * @param userId userId
     * @param resCode   resCode
     * @return true/false
     */
    Boolean userHasRes(Long userId, String resCode);

}
