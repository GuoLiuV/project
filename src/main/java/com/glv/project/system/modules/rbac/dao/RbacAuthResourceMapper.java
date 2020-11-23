package com.glv.project.system.modules.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.glv.project.system.modules.rbac.entity.RbacAuthResourceEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashSet;

/**
 * @author ZHOUXIANG
 */
public interface RbacAuthResourceMapper extends BaseMapper<RbacAuthResourceEntity> {

    /**
     * 获取用户具有资源
     *
     * @param userId 用户ID
     * @return 用户资源集合
     */
    @Select("select resource.`code` " +
            "from `t_sys_rbac_auth_resource` resource " +
            "where resource.auth_code in " +
            "(select DISTINCT(role_auth.auth_code) " +
            "from (SELECT role_code FROM `t_sys_rbac_user_role` " +
            "where user_id = #{userId}) user_role " +
            "join `t_sys_rbac_role_auth` role_auth " +
            "on role_auth.role_code = user_role.role_code) " +
            "and resource.auth_type_code = #{authType} ")
    HashSet<String> getUserRes(@Param("userId") Long userId, @Param("authType") String authType);

    /**
     * 用户是否具有该资源
     *
     * @param userId  userid
     * @param resCode rescode
     * @return true/false
     */
    @Select("select 1 from `t_sys_rbac_auth_resource` resource " +
            "where resource.auth_code in (select DISTINCT(role_auth.auth_code) " +
            "from (SELECT role_code FROM `t_sys_rbac_user_role` " +
            "where user_id = #{userId}) user_role " +
            "join `t_sys_rbac_role_auth` role_auth " +
            "on role_auth.role_code = user_role.role_code) " +
            "and resource.`code` = #{resCode}")
    Boolean userHasRes(@Param("userId") Long userId, @Param("resCode") String resCode);
}
