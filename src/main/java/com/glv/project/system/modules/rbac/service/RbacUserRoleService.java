package com.glv.project.system.modules.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.project.system.modules.rbac.dto.RbacUserRoleDto;
import com.glv.project.system.modules.rbac.entity.RbacUserRoleEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface RbacUserRoleService extends IService<RbacUserRoleEntity> {

    /**
     * 保存用户角色关系
     * @param dto 用户角色关系
     */
    void save(@Valid RbacUserRoleDto dto);

    /**
     * 清除用户角色关系
     * @param dto 用户角色关系
     */
    void delete(@Valid RbacUserRoleDto dto);

    /**
     * 获取用户关联角色ID列表
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> getIds(Long userId);

    /**
     * 获取用户所有角色
     * @param userId 用户ID
     * @return 角色编码列表
     */
    HashSet<String> getUserRoles(Long userId);

    /**
     * 判断用户是否有该角色
     * @param userId 用户ID
     * @param roleCode 角色代码
     * @return 是否有
     */
    boolean userHasRole(Long userId, String roleCode);
}
