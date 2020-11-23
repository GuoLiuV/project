package com.glv.project.system.modules.drools.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.glv.project.system.modules.drools.entity.DroolsRuleEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ZHOUXIANG
 */
public interface DroolsRuleMapper extends BaseMapper<DroolsRuleEntity> {

    /**
     * 获取所有规则，忽略租户
     *
     * @return 返回规则
     */
    @Select("select * from T_SYS_DROOLS_RULE")
    @SqlParser(filter = true)
    List<DroolsRuleEntity> listAllRules();
}
