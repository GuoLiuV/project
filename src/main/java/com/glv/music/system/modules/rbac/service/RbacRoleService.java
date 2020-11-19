package com.glv.music.system.modules.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.music.system.modules.rbac.dto.RbacRoleDto;
import com.glv.music.system.modules.rbac.entity.RbacRoleEntity;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface RbacRoleService extends IService<RbacRoleEntity> {

    /**
     * 保存角色
     * @param rbacRoleDto 角色接口模型
     */
    void saveRole(@Valid RbacRoleDto rbacRoleDto);

    /**
     * 查询代码是否重复
     * @param roleCode 角色代码
     * @param id ID
     * @return 角色信息
     */
    RbacRoleEntity getByCodeAndNotId(String roleCode, Long id);

    /**
     * 分页查询角色信息
     * @param pageRequest 查询条件
     * @return 分页数据
     */
    PageData<RbacRoleEntity> getRoleList(PageRequest<RbacRoleDto> pageRequest);

    /**
     * 删除角色
     * @param rbacRoleDto 角色接口模型
     */
    void deleteRole(RbacRoleDto rbacRoleDto);

    /**
     * 批量删除角色
     * @param ids 多个ID
     */
    void deleteBatchRole(List<Long> ids);

}
