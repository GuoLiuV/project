package com.glv.music.system.modules.scheduling.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.glv.music.system.modules.scheduling.entity.StriveSchJobEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;

/**
 * @author ZHOUXIANG
 */
public interface StriveSchJobMapper extends BaseMapper<StriveSchJobEntity> {

    /**
     * 获取所有正在运行的作业
     *
     * @param wrapper 条件构造器
     * @param handler 处理器
     */
    @Select("select * from T_SYS_SCH_JOB ${ew.customSqlSegment}")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    @ResultType(StriveSchJobEntity.class)
    @SqlParser(filter = true)
    void listRunningJob(
            @Param(Constants.WRAPPER) Wrapper<StriveSchJobEntity> wrapper,
            ResultHandler<StriveSchJobEntity> handler);

    /**
     * 更新下一次执行时间
     *
     * @param id       id
     * @param nextTime 时间
     */
    @Update("update T_SYS_SCH_JOB set next_time = #{nextTime} where id = #{id}")
    @SqlParser(filter = true)
    void updateNextTime(@Param("id") Long id, @Param("nextTime") String nextTime);
}
