package com.glv.project.system.modules.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.project.system.modules.rbac.dao.RbacRoleAuthMapper;
import com.glv.project.system.modules.rbac.dto.RbacRoleAuthDto;
import com.glv.project.system.modules.rbac.entity.RbacAuthEntity;
import com.glv.project.system.modules.rbac.entity.RbacRoleAuthEntity;
import com.glv.project.system.modules.rbac.entity.RbacRoleEntity;
import com.glv.project.system.modules.rbac.service.RbacAuthService;
import com.glv.project.system.modules.rbac.service.RbacRoleAuthService;
import com.glv.project.system.modules.rbac.service.RbacRoleService;
import com.glv.project.system.utils.CollectionUtils;
import com.glv.project.system.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Service
@Slf4j
public class RbacRoleAuthServiceImpl
        extends ServiceImpl<RbacRoleAuthMapper, RbacRoleAuthEntity>
        implements RbacRoleAuthService {

    @Resource
    private RbacAuthService rbacAuthService;

    @Resource
    private RbacRoleService rbacRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(@Valid RbacRoleAuthDto dto) {
        // 删除现在分配的
        this.delete(dto);
        // 权限再分配
        if (CollectionUtils.isNotEmpty(dto.getAuthIds())) {
            RbacRoleEntity roleEntity = rbacRoleService.getById(dto.getRoleId());
            if (ObjectUtils.notNull(roleEntity)) {
                dto.getAuthIds().forEach(authId -> {
                    RbacAuthEntity authEntity = rbacAuthService.getById(authId);
                    if (ObjectUtils.notNull(authEntity)) {
                        RbacRoleAuthEntity rbacRoleAuthEntity = new RbacRoleAuthEntity();
                        rbacRoleAuthEntity.setRoleId(roleEntity.getId())
                                .setRoleCode(roleEntity.getRoleCode())
                                .setRoleName(roleEntity.getRoleName())
                                .setAuthId(authEntity.getId())
                                .setAuthCode(authEntity.getAuthCode())
                                .setAuthName(authEntity.getAuthName());
                        this.saveOrUpdate(rbacRoleAuthEntity);
                    }
                });
            }
        }
    }

    @Override
    public void delete(@Valid RbacRoleAuthDto dto) {
        QueryWrapper<RbacRoleAuthEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", dto.getRoleId());
        this.remove(queryWrapper);
    }

    @Override
    public List<Long> getAuthIds(Long roleId) {
        List<Long> authIds = new ArrayList<>();
        QueryWrapper<RbacRoleAuthEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        List<RbacRoleAuthEntity> entities = this.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(entities)) {
            entities.forEach(en -> authIds.add(en.getAuthId()));
        }
        return authIds;
    }

    @Override
    public HashSet<String> getUserRoleAuth(HashSet<String> roleSet) {
        HashSet<String> authSet = new HashSet<>();
        if (CollectionUtils.isNotEmpty(roleSet)) {
            roleSet.forEach(rs ->
                    authSet.addAll(this.getRoleAuth(rs)));
        }
        return authSet;
    }

    @Override
    public HashSet<String> getRoleAuth(String roleCode) {
        HashSet<String> authSet = new HashSet<>();
        QueryWrapper<RbacRoleAuthEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_code", roleCode);
        List<RbacRoleAuthEntity> list = this.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(ra -> authSet.add(ra.getAuthCode()));
        }
        return authSet;
    }
}
