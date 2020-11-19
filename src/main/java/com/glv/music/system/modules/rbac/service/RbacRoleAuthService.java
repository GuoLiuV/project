package com.glv.music.system.modules.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.music.system.modules.rbac.dto.RbacRoleAuthDto;
import com.glv.music.system.modules.rbac.entity.RbacRoleAuthEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface RbacRoleAuthService extends IService<RbacRoleAuthEntity> {

    /**
     * 为角色分配权限
     * @param dto 权限信息
     */
    void save(@Valid RbacRoleAuthDto dto);

    /**
     * 删除该角色所有权限
     * @param dto 权限信息
     */
    void delete(@Valid RbacRoleAuthDto dto);

    /**
     * 根据角色ID获取所有权限ID列表
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    List<Long> getAuthIds(Long roleId);

    /**
     * 获取用户所有的权限集合
     * @param roleSet 角色集合
     * @return 权限集合
     */
    HashSet<String> getUserRoleAuth(HashSet<String> roleSet);

    /**
     * 获取角色对应的权限
     * @param roleCode 角色代码
     * @return 权限代码
     */
    HashSet<String> getRoleAuth(String roleCode);
}
