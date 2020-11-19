package com.glv.music.system.modules.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.music.system.modules.rbac.dto.RbacOrgRoleDto;
import com.glv.music.system.modules.rbac.entity.RbacOrgRoleEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface RbacOrgRoleService extends IService<RbacOrgRoleEntity> {

    /**
     * 保存组织角色关联关系信息
     * @param dto 组织关联角色
     */
    void save(@Valid RbacOrgRoleDto dto);

    /**
     * 当删除组织关联角色
     * @param dto 组织关联角色
     */
    void delete(RbacOrgRoleDto dto);

    /**
     * 根据组织ID查询它的角色
     * @param orgId 组织ID
     * @return 角色ID列表
     */
    List<Long> getIds(Long orgId);

    /**
     * 获取组织角色代码集合
     * @param orgId 组织ID
     * @return 角色代码集合
     */
    HashSet<String> getOrgRoleCode(Long orgId);

}
