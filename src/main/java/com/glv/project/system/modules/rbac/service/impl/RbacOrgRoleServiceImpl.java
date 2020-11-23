package com.glv.project.system.modules.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.project.system.modules.rbac.dao.RbacOrgRoleMapper;
import com.glv.project.system.modules.rbac.dto.RbacOrgRoleDto;
import com.glv.project.system.modules.rbac.entity.RbacOrgRoleEntity;
import com.glv.project.system.modules.rbac.entity.RbacRoleEntity;
import com.glv.project.system.modules.rbac.service.RbacOrgRoleService;
import com.glv.project.system.modules.rbac.service.RbacRoleService;
import com.glv.project.system.utils.CollectionUtils;
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
public class RbacOrgRoleServiceImpl
        extends ServiceImpl<RbacOrgRoleMapper, RbacOrgRoleEntity>
        implements RbacOrgRoleService {

    @Resource
    private RbacRoleService rbacRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(@Valid RbacOrgRoleDto dto) {
        // 清除组织角色
        this.delete(dto);
        // 设置组织角色
        if (CollectionUtils.isNotEmpty(dto.getRoleIds())) {
            dto.getRoleIds().forEach(id -> {
                RbacRoleEntity roleEntity = rbacRoleService.getById(id);
                RbacOrgRoleEntity orgRoleEntity = new RbacOrgRoleEntity();
                orgRoleEntity.setOrgId(dto.getOrgId())
                        .setRoleId(roleEntity.getId())
                        .setRoleCode(roleEntity.getRoleCode())
                        .setRoleName(roleEntity.getRoleName());
                this.saveOrUpdate(orgRoleEntity);
            });
        }
    }

    @Override
    public void delete(RbacOrgRoleDto dto) {
        QueryWrapper<RbacOrgRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id", dto.getOrgId());
        this.remove(queryWrapper);
    }

    @Override
    public List<Long> getIds(Long orgId) {
        List<Long> ids = new ArrayList<>();
        QueryWrapper<RbacOrgRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id", orgId);
        List<RbacOrgRoleEntity> list = this.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(en -> ids.add(en.getRoleId()));
        }
        return ids;
    }

    @Override
    public HashSet<String> getOrgRoleCode(Long orgId) {
        HashSet<String> roleSet = new HashSet<>();
        QueryWrapper<RbacOrgRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id", orgId);
        List<RbacOrgRoleEntity> list = this.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(or -> roleSet.add(or.getRoleCode()));
        }
        return roleSet;
    }
}
