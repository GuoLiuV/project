package com.glv.music.system.modules.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.music.system.enums.OrgTypeEnum;
import com.glv.music.system.modules.exception.StriveException;
import com.glv.music.system.modules.mybatisplus.utils.MyBatisPlusUtils;
import com.glv.music.system.modules.rbac.dao.RbacOrgMapper;
import com.glv.music.system.modules.rbac.dto.OrgTreeDto;
import com.glv.music.system.modules.rbac.dto.RbacOrgDto;
import com.glv.music.system.modules.rbac.entity.RbacOrgEntity;
import com.glv.music.system.modules.rbac.entity.RbacUserEntity;
import com.glv.music.system.modules.rbac.service.RbacOrgService;
import com.glv.music.system.modules.rbac.service.RbacUserService;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import com.glv.music.system.modules.sysadmin.SysAdminUtils;
import com.glv.music.system.utils.BeanUtils;
import com.glv.music.system.utils.CollectionUtils;
import com.glv.music.system.utils.ObjectUtils;
import com.glv.music.system.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author ZHOUXIANG
 */
@Slf4j
@Service
public class RbacOrgServiceImpl
        extends ServiceImpl<RbacOrgMapper, RbacOrgEntity>
        implements RbacOrgService {

    @Resource
    private CacheManager cacheManager;

    @Resource
    @Lazy
    private RbacUserService rbacUserService;

    @Override
    public void registerOrg(Long tenantId, @Valid RbacOrgDto dto) {
        if (ObjectUtils.isNull(tenantId)) {
            throw new StriveException("租户ID为空");
        }
        RbacOrgEntity entity = new RbacOrgEntity();
        // 设置租户ID
        entity.setTenantId(tenantId);
        entity.setOrgCode(dto.getOrgCode())
                .setOrgName(dto.getOrgName())
                .setAliasName(dto.getAliasName())
                .setOrgTypeCode(dto.getOrgTypeCode())
                .setOrgTypeName(OrgTypeEnum
                        .getAlias(dto.getOrgTypeCode()))
                .setParentId(-1L)
                .setSequence(0.0);
        try {
            // 保存组织
            this.saveOrUpdate(entity);
        } catch (DuplicateKeyException e) {
            throw new StriveException("组织代码重复");
        }
    }

    @Override
    public void saveOrg(@Valid RbacOrgDto rbacOrgDto) {
        RbacOrgEntity rbacOrgEntity = this.getByCodeAndNotId(
                rbacOrgDto.getOrgCode(), rbacOrgDto.getId());
        if (ObjectUtils.notNull(rbacOrgEntity)) {
            throw new StriveException("组织代码重复");
        } else {
            rbacOrgEntity = new RbacOrgEntity();
        }
        BeanUtils.copyProperties(rbacOrgDto, rbacOrgEntity);
        // 设置组织树ID字段
        RbacOrgEntity parent = this.getById(rbacOrgDto.getParentId());
        rbacOrgEntity.setTreeId(this.getTreeId(parent));
        // 设置组织类型名称
        rbacOrgEntity.setOrgTypeName(OrgTypeEnum
                .getAlias(rbacOrgDto.getOrgTypeCode()));
        // 清除对应租户下的缓存
        Long tenantId = SysAdminUtils.getCurrentTenantId();
        this.orgTreeCacheEvict(tenantId);
        // 保存组织
        this.saveOrUpdate(rbacOrgEntity);
    }

    @Override
    public RbacOrgEntity getByCodeAndNotId(String orgCode, Long id) {
        QueryWrapper<RbacOrgEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_code", orgCode);
        if (ObjectUtils.notNull(id)) {
            queryWrapper.ne("id", id);
        }
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean hasChildOrg(Long parentId) {
        QueryWrapper<RbacOrgEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        return this.count(queryWrapper) > 0;
    }

    @Override
    public PageData<RbacOrgEntity> getOrgList(PageRequest<RbacOrgDto> pageRequest) {
        QueryWrapper<RbacOrgEntity> queryWrapper = new QueryWrapper<>();
        MyBatisPlusUtils.buildQuery(queryWrapper, pageRequest, "orgCode");
        MyBatisPlusUtils.buildSortOrderQuery(queryWrapper, pageRequest.getSort());
        Page<RbacOrgEntity> page = pageRequest.buildMybatisPlusPage();
        IPage<RbacOrgEntity> iPage = this.page(page, queryWrapper);
        return new PageData<>(iPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrg(RbacOrgDto rbacOrgDto) {
        if (ObjectUtils.isNull(rbacOrgDto.getId())) {
            throw new StriveException("组织ID不能为空");
        }
        if (this.hasChildOrg(rbacOrgDto.getId())) {
            throw new StriveException("该组织下有子组织");
        }
        this.removeById(rbacOrgDto.getId());
        // 清缓存
        this.orgTreeCacheEvict(rbacOrgDto.getTenantId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatchOrg(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new StriveException("要删除的组织ID为空");
        }
        this.removeByIds(ids);
    }

    @Override
    public void changeTopOrgInfo(RbacOrgDto dto) {
        Long tenantId = SysAdminUtils.getCurrentTenantId();
        RbacOrgEntity top = this.getTopOrgByTenantId();
        BeanUtils.copyPropertiesIgnoreNull(dto, top);
        this.saveOrUpdate(top);
        // 清缓存
        this.orgTreeCacheEvict(tenantId);
    }

    @Override
    public RbacOrgEntity getTopOrgByTenantId() {
        QueryWrapper<RbacOrgEntity> queryWrapper = new QueryWrapper<>();
        // 当组织的父ID为空说是顶层组织，租户条件由框架统计在sql中添加，此处不用。
        queryWrapper.eq("parent_id", -1L);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<OrgTreeDto> getOrgTree() {
        final List<OrgTreeDto> parents = new ArrayList<>();
        QueryWrapper<RbacOrgEntity> queryWrapper = new QueryWrapper<>();
        Long tenantId = SysAdminUtils.getCurrentTenantId();
        if (ObjectUtils.isNull(tenantId)) {
            throw new StriveException("无法获取租户ID");
        }
        // 首先检查缓存
        List<OrgTreeDto> cacheValue = this.getOrgTreeCacheValue(tenantId);
        if (ObjectUtils.notNull(cacheValue)) {
            return cacheValue;
        }
        RbacOrgEntity topOrg = this.getTopOrgByTenantId();
        if (ObjectUtils.notNull(topOrg)) {
            OrgTreeDto orgNode = new OrgTreeDto()
                    .setTitle(topOrg.getAliasName())
                    .setExpand(true)
                    .setData(topOrg);
            orgNode.setChildren(getOrgTreeChildren(topOrg.getId()));
            parents.add(orgNode);
            // 进行缓存
            this.orgTreeCacheable(tenantId, parents);
            return parents;
        }
        return Collections.emptyList();
    }

    @Override
    public Long getTenantIdByOrgId(Long orgId) {
        RbacOrgEntity entity = this.getById(orgId);
        if (ObjectUtils.isNull(entity)) {
            throw new StriveException("获取组织所属租户失败");
        }
        // 返回组织租户
        return entity.getTenantId();
    }

    @Override
    @Cacheable(value = "sys", key = "#root.methodName + '_' + #orgCode")
    public RbacOrgEntity getOrgByOrgCode(String orgCode) {
        QueryWrapper<RbacOrgEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_code", orgCode);
        return this.getOne(queryWrapper);
    }

    @Override
    public String getOrgNameByOrgCode(String orgCode) {
        RbacOrgEntity entity = this.getOrgByOrgCode(orgCode);
        return entity.getOrgName();
    }

    @Override
    public List<RbacOrgEntity> getOrgListByOrgType(String orgType) {
        QueryWrapper<RbacOrgEntity> queryWrapper = new QueryWrapper<>();
        Long tenantId = SysAdminUtils.getCurrentTenantId();
        queryWrapper.eq("org_type_code", orgType)
                .orderByAsc("sequence");
        return this.list(queryWrapper);
    }

    @Override
    public List<RbacOrgEntity> getOrgListByParentId(Long parentId) {
        QueryWrapper<RbacOrgEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId)
                .orderByAsc("sequence");
        return this.list(queryWrapper);
    }

    @Override
    public List<RbacOrgEntity> getLevelOrgList(Long orgId) {
        List<RbacOrgEntity> orgList = new ArrayList<>();
        RbacOrgEntity bottomOrg = this.getById(orgId);
        if (ObjectUtils.notNull(bottomOrg)) {
            if (StringUtils.isNotBlank(bottomOrg.getTreeId())) {
                String[] treeIds = StringUtils.split(
                        bottomOrg.getTreeId(), ".");
                for (String treeId : treeIds) {
                    Long id = Long.parseLong(treeId);
                    orgList.add(this.getById(id));
                }
            }
            // 底层组织排在最后
            orgList.add(bottomOrg);
        }
        return orgList;
    }

    @Override
    public List<RbacOrgEntity> getUserLevelOrgList(Long userId) {
        RbacUserEntity userEntity;
        if (ObjectUtils.notNull(userId)) {
            userEntity = rbacUserService.getById(userId);
        } else {
            userEntity = SysAdminUtils.getCurrentUserEntity();
        }
        if (ObjectUtils.notNull(userEntity)) {
            return this.getLevelOrgList(userEntity.getOrgId());
        }
        return Collections.emptyList();
    }

    @Override
    public RbacOrgEntity getUserOrg(Long userId) {
        RbacUserEntity userEntity;
        if (ObjectUtils.notNull(userId)) {
            userEntity = rbacUserService.getById(userId);
        } else {
            userEntity = SysAdminUtils.getCurrentUserEntity();
        }
        if (ObjectUtils.notNull(userEntity)
                && ObjectUtils.notNull(userEntity.getOrgId())) {
            return this.getById(userEntity.getOrgId());
        }
        return null;
    }

    /**
     * 递归获取组织树
     *
     * @param parentId 父组织ID
     * @return 子组织列表
     */
    private List<OrgTreeDto> getOrgTreeChildren(Long parentId) {
        final List<OrgTreeDto> children = new ArrayList<>();
        QueryWrapper<RbacOrgEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId)
                .orderByAsc("sequence");
        List<RbacOrgEntity> orgList = this.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(orgList)) {
            orgList.forEach(org -> {
                OrgTreeDto orgNode = new OrgTreeDto()
                        .setTitle(org.getAliasName())
                        .setData(org);
                List<OrgTreeDto> childrenNode = getOrgTreeChildren(org.getId());
                if (CollectionUtils.isNotEmpty(childrenNode)) {
                    orgNode.setChildren(childrenNode);
                }
                children.add(orgNode);
            });
        }
        return children;
    }

    /**
     * 从父组织中获取层级信息
     */
    private String getTreeId(RbacOrgEntity parent) {
        // 顶层组织的treeId为null
        if (ObjectUtils.isNull(parent.getTreeId())) {
            return String.valueOf(parent.getId());
        }
        return parent.getTreeId().concat(".")
                .concat(String.valueOf(parent.getId()));
    }

    /**
     * 清除对应租户的缓存
     */
    private void orgTreeCacheEvict(Long tenantId) {
        if (ObjectUtils.isNull(tenantId)) {
            return;
        }
        String cacheKey = "tenant";
        cacheKey = cacheKey.concat(String.valueOf(tenantId));
        Objects.requireNonNull(cacheManager.getCache("sys")).evict(cacheKey);
    }

    /**
     * 缓存租户的组织树
     */
    private void orgTreeCacheable(Long tenantId, Object value) {
        if (ObjectUtils.isNull(tenantId)) {
            return;
        }
        String cacheKey = "tenant";
        cacheKey = cacheKey.concat(String.valueOf(tenantId));
        Objects.requireNonNull(cacheManager.getCache("sys")).put(cacheKey, value);
    }

    /**
     * 根据键获取缓存值
     */
    private List<OrgTreeDto> getOrgTreeCacheValue(Long tenantId) {
        if (ObjectUtils.isNull(tenantId)) {
            return null;
        }
        String cacheKey = "tenant";
        cacheKey = cacheKey.concat(String.valueOf(tenantId));
        Cache cache = cacheManager.getCache("sys");
        if (ObjectUtils.notNull(cache)) {
            Cache.ValueWrapper valueWrapper = cache.get(cacheKey);
            if (ObjectUtils.notNull(valueWrapper)) {
                Object obj = valueWrapper.get();
                List<OrgTreeDto> treeDtos = new ArrayList<>();
                if (obj instanceof ArrayList) {
                    for (Object o : (List<?>)obj) {
                        treeDtos.add((OrgTreeDto) o);
                    }
                }
                return treeDtos;
            }
        }
        return null;
    }
}
