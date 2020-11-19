package com.glv.music.system.modules.scheduling.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.glv.music.system.modules.scheduling.entity.StriveSchRecordEntity;
import org.apache.ibatis.annotations.Update;

/**
 * @author ZHOUXIANG
 */
public interface StriveSchRecordMapper extends BaseMapper<StriveSchRecordEntity> {

    /**
     * 更新异常堆栈
     *
     * @param entity 实体
     */
    @SqlParser(filter = true)
    @Update("update T_SYS_SCH_RECORD set status = #{status}, stack_trace = #{stackTrace}, end_time = #{endTime} where id = #{id}")
    void updateStackTrace(StriveSchRecordEntity entity);

    /**
     * 更新状态
     *
     * @param entity 实体
     */
    @SqlParser(filter = true)
    @Update("update T_SYS_SCH_RECORD set status = #{status}, end_time = #{endTime} where id = #{id}")
    void updateStatus(StriveSchRecordEntity entity);
}
