package com.glv.project.system.modules.oauth2.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.glv.project.system.modules.oauth2.entity.ClientDetailsEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 访问Oauth2客户端信息的MyBatis Mapper
 *
 * @author ZHOUXIANG
 */
public interface StriveClientDetailsMapper extends BaseMapper<ClientDetailsEntity> {

    /**
     * 根据clientId查询应用信息
     * @param  wrapper 条件
     * @return 应用信息
     */
    @Select("select * from T_SYS_AUTH_CLIENT ${ew.customSqlSegment}")
    @SqlParser(filter = true)
    ClientDetailsEntity getClientByClientId(
            @Param(Constants.WRAPPER)
                    QueryWrapper<ClientDetailsEntity> wrapper);
}
