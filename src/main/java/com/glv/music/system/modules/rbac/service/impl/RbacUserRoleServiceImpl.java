package com.glv.music.system.modules.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.music.system.modules.rbac.dao.RbacUserRoleMapper;
import com.glv.music.system.modules.rbac.dto.RbacUserRoleDto;
import com.glv.music.system.modules.rbac.entity.RbacRoleEntity;
import com.glv.music.system.modules.rbac.entity.RbacUserEntity;
import com.glv.music.system.modules.rbac.entity.RbacUserRoleEntity;
import com.glv.music.system.modules.rbac.service.RbacOrgRoleService;
import com.glv.music.system.modules.rbac.service.RbacRoleService;
import com.glv.music.system.modules.rbac.service.RbacUserRoleService;
import com.glv.music.system.modules.rbac.service.RbacUserService;
import com.glv.music.system.modules.sysadmin.SysAdminUtils;
import com.glv.music.system.utils.CollectionUtils;
import com.glv.music.system.utils.ObjectUtils;
import com.glv.music.system.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
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
public class RbacUserRoleServiceImpl
        extends ServiceImpl<RbacUserRoleMapper, RbacUserRoleEntity>
        implements RbacUserRoleService {

    @Resource
    private RbacRoleService rbacRoleService;

    @Resource
    private RbacOrgRoleService rbacOrgRoleService;

    @Resource
    @Lazy
    private RbacUserService rbacUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(@Valid RbacUserRoleDto dto) {
        // 清除用户角色
        this.delete(dto);
        // 保存用户角色
        if (CollectionUtils.isNotEmpty(dto.getRoleIds())) {
            dto.getRoleIds().forEach(roleId -> {
                RbacRoleEntity roleEntity = rbacRoleService.getById(roleId);
                dto.getUserIds().forEach(userId -> {
                    RbacUserRoleEntity userRoleEntity = new RbacUserRoleEntity();
                    userRoleEntity.setUserId(userId)
                            .setRoleId(roleEntity.getId())
                            .setRoleCode(roleEntity.getRoleCode())
                            .setRoleName(roleEntity.getRoleName());
                    this.saveOrUpdate(userRoleEntity);
                });
            });
        }
    }

    @Override
    public void delete(@Valid RbacUserRoleDto dto) {
        QueryWrapper<RbacUserRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id", dto.getUserIds());
        this.remove(queryWrapper);
    }

    @Override
    public List<Long> getIds(Long userId) {
        List<Long> ids = new ArrayList<>();
        QueryWrapper<RbacUserRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<RbacUserRoleEntity> list = this.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(en -> ids.add(en.getRoleId()));
        }
        return ids;
    }

    @Override
    public HashSet<String> getUserRoles(Long userId) {
        HashSet<String> userRoleSet = new HashSet<>();
        RbacUserEntity userEntity;
        if (ObjectUtils.notNull(userId)) {
            userEntity = rbacUserService.getById(userId);
        } else {
            userEntity = SysAdminUtils.getCurrentUserEntity();
        }
        if (ObjectUtils.notNull(userEntity)) {
            QueryWrapper<RbacUserRoleEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userEntity.getId());
            List<RbacUserRoleEntity> userRoleList = this.list(queryWrapper);
            if (CollectionUtils.isNotEmpty(userRoleList)) {
                userRoleList.forEach(ur -> userRoleSet.add(ur.getRoleCode()));
            }
            if (ObjectUtils.notNull(userEntity.getOrgId())) {
                HashSet<String> orgRoleSet =
                        rbacOrgRoleService.getOrgRoleCode(userEntity.getOrgId());
                userRoleSet.addAll(orgRoleSet);
            }
        }
        return userRoleSet;
    }

    @Override
    public boolean userHasRole(Long userId, String roleCode) {
        HashSet<String> roleCodeSet = this.getUserRoles(userId);
        if (CollectionUtils.isNotEmpty(roleCodeSet)) {
            for (String s : roleCodeSet) {
                if (StringUtils.equalsIgnoreCase(s, roleCode)) {
                    return true;
                }
            }
        }
        return false;
    }
}
