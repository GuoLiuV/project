package com.glv.project.system.modules.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.project.system.modules.rbac.dao.RbacAuthResourceMapper;
import com.glv.project.system.modules.rbac.dto.RbacAuthResourceDto;
import com.glv.project.system.modules.rbac.entity.*;
import com.glv.project.system.modules.rbac.service.*;
import com.glv.project.system.modules.sysadmin.SysAdminUtils;
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
public class RbacAuthResourceServiceImpl
        extends ServiceImpl<RbacAuthResourceMapper, RbacAuthResourceEntity>
        implements RbacAuthResourceService {

    @Resource
    private RbacAuthService rbacAuthService;

    @Resource
    private RbacMenuService rbacMenuService;

    @Resource
    private RbacElemService rbacElemService;

    @Resource
    private RbacResService rbacResService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(@Valid RbacAuthResourceDto dto) {
        // 删除已有关联
        this.delete(dto);
        // 设置关联关系
        RbacAuthEntity authEntity = rbacAuthService.getById(dto.getAuthId());
        if (ObjectUtils.notNull(authEntity)) {
            if (CollectionUtils.isNotEmpty(dto.getResourceIds())) {
                switch (authEntity.getAuthTypeCode()) {
                    case "MENU":
                        this.handleMenu(dto, authEntity);
                        break;
                    case "ELEMENT":
                        this.handleElem(dto, authEntity);
                        break;
                    case "RESOURCE":
                        this.handleRes(dto, authEntity);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void delete(@Valid RbacAuthResourceDto dto) {
        QueryWrapper<RbacAuthResourceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("auth_id", dto.getAuthId());
        this.remove(queryWrapper);
    }

    @Override
    public List<Long> getIds(Long authId) {
        List<Long> ids = new ArrayList<>();
        QueryWrapper<RbacAuthResourceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("auth_id", authId);
        List<RbacAuthResourceEntity> list = this.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(en -> ids.add(en.getResId()));
        }
        return ids;
    }

    @Override
    public HashSet<String> getUserAuthResource(HashSet<String> auths, String authType) {
        HashSet<String> hashSet = new HashSet<>();
        if (CollectionUtils.isNotEmpty(auths)) {
            auths.forEach(au -> hashSet.addAll(this.getAuthResource(au, authType)));
        }
        return hashSet;
    }

    @Override
    public HashSet<String> getAuthResource(String authCode, String authType) {
        HashSet<String> hashSet = new HashSet<>();
        QueryWrapper<RbacAuthResourceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("auth_code", authCode)
                .eq("auth_type_code", authType);
        List<RbacAuthResourceEntity> list = this.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(ar -> hashSet.add(ar.getCode()));
        }
        return hashSet;
    }

    @Override
    public HashSet<String> getUserRes(Long userId, String type) {
        if (ObjectUtils.notNull(userId)) {
            return this.baseMapper.getUserRes(userId, type);
        } else {
            RbacUserEntity userEntity = SysAdminUtils.getCurrentUserEntity();
            if (ObjectUtils.notNull(userEntity)) {
                return this.baseMapper.getUserRes(userEntity.getId(), type);
            }
        }
        return new HashSet<>();
    }

    @Override
    public Boolean userHasRes(Long userId, String resCode) {
        Boolean has = false;
        if (ObjectUtils.notNull(userId)) {
            has = this.baseMapper.userHasRes(userId, resCode);
        } else {
            RbacUserEntity userEntity = SysAdminUtils.getCurrentUserEntity();
            if (ObjectUtils.notNull(userEntity)) {
                has = this.baseMapper.userHasRes(userEntity.getId(), resCode);
            }
        }
        return has != null && has;
    }

    /**
     * 保存权限菜单关系
     * @param dto 权限菜单关系
     * @param authEntity 权限信息
     */
    private void handleMenu(RbacAuthResourceDto dto, RbacAuthEntity authEntity) {
        dto.getResourceIds().forEach(id -> {
            RbacMenuEntity menuEntity = rbacMenuService.getById(id);
            if (ObjectUtils.notNull(menuEntity)) {
                RbacAuthResourceEntity rbacAuthResourceEntity
                        = new RbacAuthResourceEntity();
                rbacAuthResourceEntity.setAuthId(authEntity.getId())
                        .setAuthCode(authEntity.getAuthCode())
                        .setAuthName(authEntity.getAuthName())
                        .setAuthTypeCode(authEntity.getAuthTypeCode())
                        .setAuthTypeName(authEntity.getAuthTypeName())
                        .setResId(menuEntity.getId())
                        .setCode(menuEntity.getMenuCode())
                        .setName(menuEntity.getMenuName());
                this.saveOrUpdate(rbacAuthResourceEntity);
            }
        });
    }

    /**
     * 保存权限元素关系
     * @param dto 权限元素关系
     * @param authEntity 权限信息
     */
    private void handleElem(RbacAuthResourceDto dto, RbacAuthEntity authEntity) {
        dto.getResourceIds().forEach(id -> {
            RbacElemEntity elemEntity = rbacElemService.getById(id);
            if (ObjectUtils.notNull(elemEntity)) {
                RbacAuthResourceEntity rbacAuthResourceEntity
                        = new RbacAuthResourceEntity();
                rbacAuthResourceEntity.setAuthId(authEntity.getId())
                        .setAuthCode(authEntity.getAuthCode())
                        .setAuthName(authEntity.getAuthName())
                        .setAuthTypeCode(authEntity.getAuthTypeCode())
                        .setAuthTypeName(authEntity.getAuthTypeName())
                        .setResId(elemEntity.getId())
                        .setCode(elemEntity.getElemCode())
                        .setName(elemEntity.getElemName());
                this.saveOrUpdate(rbacAuthResourceEntity);
            }
        });
    }

    /**
     * 保存权限资源关系
     * @param dto 权限资源关系
     * @param authEntity 权限信息
     */
    private void handleRes(RbacAuthResourceDto dto, RbacAuthEntity authEntity) {
        dto.getResourceIds().forEach(id -> {
            RbacResEntity resEntity = rbacResService.getById(id);
            if (ObjectUtils.notNull(resEntity)) {
                RbacAuthResourceEntity rbacAuthResourceEntity
                        = new RbacAuthResourceEntity();
                rbacAuthResourceEntity.setAuthId(authEntity.getId())
                        .setAuthCode(authEntity.getAuthCode())
                        .setAuthName(authEntity.getAuthName())
                        .setAuthTypeCode(authEntity.getAuthTypeCode())
                        .setAuthTypeName(authEntity.getAuthTypeName())
                        .setResId(resEntity.getId())
                        .setCode(resEntity.getResUri())
                        .setName(resEntity.getResName());
                this.saveOrUpdate(rbacAuthResourceEntity);
            }
        });
    }
}
