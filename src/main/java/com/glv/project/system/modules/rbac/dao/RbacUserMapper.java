package com.glv.project.system.modules.rbac.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.glv.project.system.modules.rbac.entity.RbacUserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author ZHOUXIANG
 */
public interface RbacUserMapper extends BaseMapper<RbacUserEntity> {

    /**
     * 根据登录名获取用户的租户
     * @param wrapper 条件
     * @return 租户
     */
    @SqlParser(filter = true)
    @Select("select * from t_sys_rbac_user ${ew.customSqlSegment}")
    RbacUserEntity getUserByLoginName(@Param(Constants.WRAPPER) QueryWrapper<RbacUserEntity> wrapper);

    /**
     * 更新租户ID
     * @param entity 用户信息
     */
    @SqlParser(filter = true)
    @Update("update t_sys_rbac_user set login_name = #{loginName}, password = #{password} " +
            "where id = #{id} and tenant_id = '-1' ")
    void updateTenantById(RbacUserEntity entity);
}
