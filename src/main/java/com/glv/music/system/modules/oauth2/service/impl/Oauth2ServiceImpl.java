package com.glv.music.system.modules.oauth2.service.impl;

import com.glv.music.system.enums.AuthTypeEnum;
import com.glv.music.system.enums.SuperAdminEnum;
import com.glv.music.system.modules.exception.StriveNoAuthException;
import com.glv.music.system.modules.oauth2.dto.LoginUserInfoDto;
import com.glv.music.system.modules.oauth2.service.Oauth2Service;
import com.glv.music.system.modules.rbac.entity.RbacUserEntity;
import com.glv.music.system.modules.rbac.service.*;
import com.glv.music.system.modules.sysadmin.SysAdminUtils;
import com.glv.music.system.utils.ObjectUtils;
import com.glv.music.system.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ZHOUXIANG
 */
@Service
@Slf4j
@AllArgsConstructor
public class Oauth2ServiceImpl implements Oauth2Service {

    private RbacUserService rbacUserService;

    private RbacOrgService rbacOrgService;

    private RbacUserRoleService rbacUserRoleService;

    private RbacRoleAuthService rbacRoleAuthService;

    private RbacAuthResourceService rbacAuthResourceService;

    @Override
    public LoginUserInfoDto getLoginUserInfo() {
        LoginUserInfoDto dto = new LoginUserInfoDto();
        String userName = SysAdminUtils.getCurrentUserLoginName();
        if (ObjectUtils.isNull(userName)) {
            throw new StriveNoAuthException("没有权限获取当前用户");
        }
        // 用户基本信息
        RbacUserEntity user = rbacUserService.getUserByLoginName(userName);
        if (StringUtils.equals(userName, SuperAdminEnum.LOGIN_NAME.value)) {
            user = SysAdminUtils.getCurrentUserEntity();
        }
        if (ObjectUtils.isNull(user)) {
            throw new StriveNoAuthException("没有权限获取当前用户");
        }
        dto.setUser(user);
        // 组织信息
        if (ObjectUtils.notNull(user.getOrgId())) {
            dto.setOrgs(rbacOrgService
                    .getLevelOrgList(user.getOrgId()));
        }
        // 角色集合
        dto.setRoles(rbacUserRoleService
                .getUserRoles(user.getId()));
        // 权限集合
        dto.setAuths(rbacRoleAuthService
                .getUserRoleAuth(dto.getRoles()));
        // 菜单集合
        dto.setMenus(rbacAuthResourceService
                .getUserAuthResource(
                        dto.getAuths(), AuthTypeEnum.MENU.name()));
        // 元素集合
        dto.setElements(rbacAuthResourceService
                .getUserAuthResource(
                        dto.getAuths(), AuthTypeEnum.ELEMENT.name()));
        // 资源集合
        dto.setResources(rbacAuthResourceService
                .getUserAuthResource(
                        dto.getAuths(), AuthTypeEnum.RESOURCE.name()));
        return dto;
    }
}
