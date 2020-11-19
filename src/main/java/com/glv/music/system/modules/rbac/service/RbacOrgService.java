package com.glv.music.system.modules.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.music.system.modules.rbac.dto.OrgTreeDto;
import com.glv.music.system.modules.rbac.dto.RbacOrgDto;
import com.glv.music.system.modules.rbac.entity.RbacOrgEntity;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface RbacOrgService extends IService<RbacOrgEntity> {

    /**
     * 生成租户的顶层组织名称
     *
     * @param tenantId 租户ID
     * @param dto      组织信息
     */
    void registerOrg(Long tenantId, @Valid RbacOrgDto dto);

    /**
     * 保存组织
     *
     * @param rbacOrgDto 接口模型
     */
    void saveOrg(@Valid RbacOrgDto rbacOrgDto);

    /**
     * 判断该租户下的组织代码是否重复
     *
     * @param orgCode 组织代码
     * @param id      id
     * @return 组织信息
     */
    RbacOrgEntity getByCodeAndNotId(String orgCode, Long id);

    /**
     * 分页查询组织
     *
     * @param pageRequest 接口模型
     * @return 分页数据
     */
    PageData<RbacOrgEntity> getOrgList(PageRequest<RbacOrgDto> pageRequest);

    /**
     * 是否有子组织
     *
     * @param parentId 父组织ID
     * @return true 有子组织
     */
    boolean hasChildOrg(Long parentId);

    /**
     * 根据ID删除组织
     *
     * @param rbacOrgDto 接口模型
     */
    void deleteOrg(RbacOrgDto rbacOrgDto);

    /**
     * 根据ID批量删除组织
     *
     * @param ids id集合
     */
    void deleteBatchOrg(List<Long> ids);

    /**
     * 修改顶层组织信息
     *
     * @param dto 组织信息
     */
    void changeTopOrgInfo(RbacOrgDto dto);

    /**
     * 租户的顶层组织
     *
     * @return 顶层组织
     */
    RbacOrgEntity getTopOrgByTenantId();

    /**
     * 获取组织树
     *
     * @return 组织树数组
     */
    List<OrgTreeDto> getOrgTree();

    /**
     * 获取组织所属的租户ID
     *
     * @param orgId 组织ID
     * @return 租户ID
     */
    Long getTenantIdByOrgId(Long orgId);

    /**
     * 根据组织代码获取组织
     *
     * @param orgCode 组织代码
     * @return 组织
     */
    RbacOrgEntity getOrgByOrgCode(String orgCode);

    /**
     * 根据组织代码获取组织名称
     *
     * @param orgCode 组织代码
     * @return 组织名称
     */
    String getOrgNameByOrgCode(String orgCode);

    /**
     * 根据组织类型查询
     *
     * @param orgType 组织类型
     * @return 组织列表
     */
    List<RbacOrgEntity> getOrgListByOrgType(String orgType);

    /**
     * 根据父组织ID获取子组织
     *
     * @param parentId 父组织ID
     * @return 组织列表
     */
    List<RbacOrgEntity> getOrgListByParentId(Long parentId);

    /**
     * 获取用户所在的所有层级的组织列表
     *
     * @param orgId 用户所在直接组织
     * @return 组织列表
     */
    List<RbacOrgEntity> getLevelOrgList(Long orgId);

    /**
     * 获取用户所在的所有层级的组织列表
     *
     * @param userId 用户ID
     * @return 组织列表
     */
    List<RbacOrgEntity> getUserLevelOrgList(Long userId);

    /**
     * 获取用户组织
     *
     * @param userId 用户ID
     * @return 组织
     */
    RbacOrgEntity getUserOrg(Long userId);

}
