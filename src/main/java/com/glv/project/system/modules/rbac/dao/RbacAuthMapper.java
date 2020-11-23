package com.glv.project.system.modules.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.glv.project.system.modules.rbac.entity.RbacAuthEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashSet;

/**
 * @author ZHOUXIANG
 */
public interface RbacAuthMapper extends BaseMapper<RbacAuthEntity> {

    /**
     * 获取用户权限
     *
     * @param userId 用户ID
     * @return 权限代码集合
     */
    @Select("select DISTINCT(role_auth.auth_code) " +
            "from (SELECT role_code FROM `t_sys_rbac_user_role` " +
            "where user_id = #{userId}) user_role " +
            "join t_sys_rbac_role_auth role_auth " +
            "on role_auth.role_code = user_role.role_code")
    HashSet<String> getUserAuths(@Param("userId") Long userId);

    /**
     * 判断用户是否具有某权限
     *
     * @param userId   用户ID
     * @param authCode 权限代码
     * @return 是否具有
     */
    @Select("select 1 where EXISTS(select DISTINCT(role_auth.auth_code) " +
            "from (SELECT role_code FROM `t_sys_rbac_user_role` " +
            "where user_id = #{userId}) user_role " +
            "join `t_sys_rbac_role_auth` role_auth " +
            "on role_auth.role_code = user_role.role_code " +
            "where role_auth.auth_code = #{authCode})")
    Boolean userHasAuth(@Param("userId") Long userId, @Param("authCode") String authCode);
}
