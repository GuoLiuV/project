package com.glv.project.system.modules.scheduling.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.glv.project.system.modules.scheduling.entity.StriveSchExecutorEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author ZHOUXIANG
 */
public interface StriveSchExecutorMapper extends BaseMapper<StriveSchExecutorEntity> {

    /**
     * 根据ID查询执行器
     *
     * @param id id
     * @return 执行器
     */
    @Select("select * from T_SYS_SCH_EXECUTOR where id = #{id}")
    @SqlParser(filter = true)
    StriveSchExecutorEntity getExecutorById(@Param("id") Long id);

    /**
     * 根据ID查询执行器服务地址列表
     *
     * @param id id
     * @return 执行器地址列表
     */
    @Select("select url from T_SYS_SCH_EXECUTOR where id = #{id}")
    @SqlParser(filter = true)
    String getUrlsById(@Param("id") Long id);
}
