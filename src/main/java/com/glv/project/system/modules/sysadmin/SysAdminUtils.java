package com.glv.project.system.modules.sysadmin;

import com.glv.project.system.enums.AuthTypeEnum;
import com.glv.project.system.enums.OperateStatusEnum;
import com.glv.project.system.modules.dict.entity.DictDataEntity;
import com.glv.project.system.modules.dict.service.DictDataService;
import com.glv.project.system.modules.rbac.entity.RbacOrgEntity;
import com.glv.project.system.modules.rbac.entity.RbacUserEntity;
import com.glv.project.system.modules.rbac.service.*;
import com.glv.project.system.modules.web.component.SpringContextHolder;
import com.glv.project.system.utils.ObjectUtils;
import com.glv.project.system.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.HashSet;
import java.util.List;

/**
 * RBAC用户操作工具类
 *
 * @author ZHOUXIANG
 */
@Slf4j
public class SysAdminUtils {

    /**
     * 获取登录用户的token
     */
    public static String getCurrentLoginToken() {
        try {
            SecurityContext ctx = SecurityContextHolder.getContext();
            if (ObjectUtils.notNull(ctx)) {
                Authentication auth = ctx.getAuthentication();
                if (ObjectUtils.notNull(auth)) {
                    Object details = auth.getDetails();
                    if (details instanceof OAuth2AuthenticationDetails) {
                        OAuth2AuthenticationDetails authDetails = (OAuth2AuthenticationDetails) details;
                        return String.valueOf(authDetails.getTokenValue());
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取用户登录信息失败", e);
        }
        return null;
    }

    /**
     * 获取登录用户的登录名
     *
     * @return loginname
     */
    public static String getCurrentUserLoginName() {
        try {
            SecurityContext ctx = SecurityContextHolder.getContext();
            Authentication auth = ctx.getAuthentication();
            if (auth instanceof OAuth2Authentication) {
                return auth.getName();
            }
        } catch (Exception e) {
            log.error("获取用户登录名失败", e);
        }
        return null;
    }

    /**
     * 获取当前登录用户的数据库信息
     *
     * @return 用户信息
     */
    public static RbacUserEntity getCurrentUserEntity() {
        RbacUserService rbacUserService =
                SpringContextHolder.getBean(RbacUserService.class);
        String loginName = getCurrentUserLoginName();
        if (ObjectUtils.notNull(loginName)) {
            RbacUserEntity entity = rbacUserService.getUserByLoginName(loginName);
            if (ObjectUtils.notNull(entity)) {
                return entity;
            }
        }
        return null;
    }

    /**
     * 获取当前租户ID，就是当前登录用户的ID
     *
     * @return 租户ID
     */
    public static Long getCurrentTenantId() {
        RbacUserEntity rbacUserEntity = getCurrentUserEntity();
        if (ObjectUtils.isNull(rbacUserEntity)) {
            // 租户的tenantId为-1，避开mysql主键
            return -1L;
        }
        if (StringUtils.equalsIgnoreCase(
                rbacUserEntity.getIsTenant(),
                OperateStatusEnum.Y.name())) {
            // 租户自己的ID就是自己的ID
            return rbacUserEntity.getId();
        }
        // 非租户本身就返回租户ID
        return rbacUserEntity.getTenantId();
    }

    /**
     * 获取当前用户角色
     *
     * @return 当前用户角色代码集合
     */
    public static HashSet<String> getUserRoles() {
        RbacUserRoleService userRoleService =
                SpringContextHolder.getBean(RbacUserRoleService.class);
        return userRoleService.getUserRoles(null);
    }

    /**
     * 判断用户是否具有该角色
     *
     * @param roleCode 角色代码
     * @return true/false
     */
    public static Boolean userHasRole(String roleCode) {
        RbacUserRoleService userRoleService =
                SpringContextHolder.getBean(RbacUserRoleService.class);
        return userRoleService.userHasRole(null, roleCode);
    }

    /**
     * 获取当前登录用户的权限
     *
     * @return 当前登录用户的权限集合
     */
    public static HashSet<String> getUserAuths() {
        RbacAuthService authService =
                SpringContextHolder.getBean(RbacAuthService.class);
        return authService.getUserAuths(null);
    }

    /**
     * 判断用户是否具有该权限
     *
     * @param authCode 权限代码
     * @return true / false
     */
    public static Boolean userHasAuth(String authCode) {
        RbacAuthService authService =
                SpringContextHolder.getBean(RbacAuthService.class);
        return authService.userHasAuth(null, authCode);
    }

    /**
     * 获取当前登录用户菜单集合
     *
     * @return 菜单集合
     */
    public static HashSet<String> getUserMenus() {
        RbacAuthResourceService resourceService =
                SpringContextHolder.getBean(RbacAuthResourceService.class);
        return resourceService.getUserRes(null, AuthTypeEnum.MENU.name());
    }

    /**
     * 获取当前用户元素集合
     *
     * @return 当前用户元素集合
     */
    public static HashSet<String> getUserElems() {
        RbacAuthResourceService resourceService =
                SpringContextHolder.getBean(RbacAuthResourceService.class);
        return resourceService.getUserRes(null, AuthTypeEnum.ELEMENT.name());
    }

    /**
     * 获取当前用户资源集合
     *
     * @return 当前用户资源集合
     */
    public static HashSet<String> getUserRes() {
        RbacAuthResourceService resourceService =
                SpringContextHolder.getBean(RbacAuthResourceService.class);
        return resourceService.getUserRes(null, AuthTypeEnum.RESOURCE.name());
    }

    /**
     * 用户是否具有该资源
     *
     * @param resCode 资源代码
     * @return true / false
     */
    public static Boolean userHasRes(String resCode) {
        RbacAuthResourceService resourceService =
                SpringContextHolder.getBean(RbacAuthResourceService.class);
        return resourceService.userHasRes(null, resCode);
    }

    /**
     * 获取当前用户所在组织
     *
     * @return 组织
     */
    public static RbacOrgEntity getCurrentUserOrg() {
        RbacOrgService rbacOrgService =
                SpringContextHolder.getBean(RbacOrgService.class);
        return rbacOrgService.getUserOrg(null);
    }

    /**
     * 根据父代码查询字典项
     *
     * @param parentCode 父代码
     * @return 字典项
     */
    public static List<DictDataEntity> getDictByParentCode(String parentCode) {
        DictDataService dictDataService =
                SpringContextHolder.getBean(DictDataService.class);
        return dictDataService.getDictByParentCode(parentCode);
    }

    /**
     * 通过字典代码获取字典值
     *
     * @param code 字典代码
     * @return 字典值
     */
    public static String getDictValueByCode(String code) {
        DictDataService dictDataService =
                SpringContextHolder.getBean(DictDataService.class);
        return dictDataService.getDictValueByCode(code);
    }

}
