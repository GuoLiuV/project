package com.glv.project.system.modules.sysadmin.service;

import com.glv.project.system.modules.dict.entity.DictDataEntity;
import com.glv.project.system.modules.rbac.entity.RbacOrgEntity;
import com.glv.project.system.modules.rbac.entity.RbacUserEntity;
import com.glv.project.system.modules.response.dto.ResponseStatusDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@FeignClient(name = "STRIVE", url = "http://localhost:8080", path = "/strive")
public interface SysAdminService {

    /**
     * 获取登录用户的登录名
     *
     * @return loginName
     */
    @GetMapping(value = "/admin/sys/user/getCurrentLoginName",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseStatusDto<String> getCurrentUserLoginName();

    /**
     * 获取当前登录用户的数据库信息
     *
     * @return 用户信息
     */
    @GetMapping(value = "/admin/sys/user/getCurrentUserEntity",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseStatusDto<RbacUserEntity> getCurrentUserEntity();

    /**
     * 获取当前租户ID，就是当前登录用户的ID
     *
     * @return 租户ID
     */
    @GetMapping(value = "/admin/sys/user/getCurrentTenantId",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseStatusDto<Long> getCurrentTenantId();

    /**
     * 获取当前用户角色
     *
     * @return 当前用户角色代码集合
     */
    @GetMapping(value = "/admin/sys/user-role/getUserRoles",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseStatusDto<HashSet<String>> getUserRoles();

    /**
     * 判断用户是否具有该角色
     *
     * @param roleCode 角色代码
     * @return true/false
     */
    @GetMapping(value = "/admin/sys/user-role/userHasRole",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseStatusDto<Boolean> userHasRole(
            @RequestParam("roleCode") String roleCode);

    /**
     * 获取当前登录用户的权限
     *
     * @return 当前登录用户的权限集合
     */
    @GetMapping(value = "/admin/sys/auth/getUserAuths",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseStatusDto<HashSet<String>> getUserAuths();

    /**
     * 判断用户是否具有该权限
     *
     * @param authCode 权限代码
     * @return true / false
     */
    @GetMapping(value = "/admin/sys/auth/userHasAuth",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseStatusDto<Boolean> userHasAuth(
            @RequestParam("authCode") String authCode);

    /**
     * 获取当前登录用户各种资源集合
     *
     * @param userId   userId
     * @param authType authType
     * @return 种资源集合
     */
    @GetMapping(value = "/admin/sys/auth-resource/getUserRes",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseStatusDto<HashSet<String>> getUserRes(
            @RequestParam("userId") Long userId,
            @RequestParam("authType") String authType);

    /**
     * 用户是否具有该资源
     *
     * @param userId  userId
     * @param resCode 资源代码
     * @return true / false
     */
    @GetMapping(value = "/admin/sys/auth-resource/userHasRes",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseStatusDto<Boolean> userHasRes(
            @RequestParam("userId") Long userId,
            @RequestParam("resCode") String resCode);

    /**
     * 获取用户所在组织
     *
     * @param userId 用户ID
     * @return 组织
     */
    @GetMapping(value = "/admin/sys/org/getUserOrg",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseStatusDto<RbacOrgEntity> getUserOrg(
            @RequestParam("userId") Long userId);

    /**
     * 获取字典列表
     *
     * @param parentCode 父字典项
     * @return 子字典列表
     */
    @GetMapping(value = "/admin/sys/dict/getChildDict",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseStatusDto<List<DictDataEntity>> getDictByParentCode(
            @RequestParam("parentCode") String parentCode);

    /**
     * 通过代码获取字典值
     *
     * @param code 字典代码
     * @return 字典值
     */
    @GetMapping(value = "/admin/sys/dict/getDictValueByCode",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseStatusDto<String> getDictValueByCode(
            @RequestParam("code") String code);
}
