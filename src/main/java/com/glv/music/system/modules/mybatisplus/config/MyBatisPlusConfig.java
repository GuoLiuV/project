package com.glv.music.system.modules.mybatisplus.config;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.glv.music.system.modules.exception.StriveException;
import com.glv.music.system.modules.sysadmin.SysAdminUtils;
import com.glv.music.system.utils.ObjectUtils;
import com.glv.music.system.utils.ReflectionUtils;
import com.glv.music.system.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.ibatis.mapping.MappedStatement;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZHOUXIANG
 */
@Slf4j
@Configuration
@MapperScan(value = {"com.**.dao"})
public class MyBatisPlusConfig {

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @Bean
    public DynamicTableNameParser dynamicTableNameParser() {
        // 动态表名解析器
        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
        Map<String, ITableNameHandler> handlerMap = new HashMap<>(5);
        dynamicTableNameParser.setTableNameHandlerMap(handlerMap);
        return dynamicTableNameParser;
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(DynamicTableNameParser dynamicTableNameParser) {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 添加租户sql解析链，如果是开发业务系统，可以去掉这个配置，不去也可以。
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(tenantHandler());
        sqlParserList.add(tenantSqlParser);
        // 设置sql解析链
        paginationInterceptor.setSqlParserList(sqlParserList);
        // 设置过滤器链
        paginationInterceptor.setSqlParserFilter(sqlParserFilter());
        return paginationInterceptor;
    }

    @Bean
    public TenantHandler tenantHandler() {
        return new TenantHandler() {
            @Override
            public Expression getTenantId(boolean where) {
                // 返回当前登录人员的租户ID
                return new LongValue(
                        SysAdminUtils.getCurrentTenantId());
            }
            @Override
            public String getTenantIdColumn() {
                return "tenant_id";
            }
            @Override
            public boolean doTableFilter(String tableName) {
                return false;
            }
        };
    }

    /**
     * 如何有自定义无租户的查询，可以在此过滤
     * @return SQL解析过滤
     */
    @Bean
    public ISqlParserFilter sqlParserFilter() {
        return metaObject -> {
            // 如果在程序中已经手动设置了tenant_id，此处就过滤
            boolean insertFilter,
                    updateByIdFilter = false,
                    selectByIdFilter = false;
            Object boundSql = metaObject.getValue("boundSql");
            String sql = String.valueOf(ReflectionUtils
                    .getFieldValue(boundSql, "sql"));
            insertFilter = StringUtils.containsIgnoreCase(sql, "insert")
                    && StringUtils.containsIgnoreCase(sql, "tenant_id");
            MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);
            if (ObjectUtils.notNull(ms.getId())) {
                updateByIdFilter = ms.getId().contains("updateById");
                selectByIdFilter = ms.getId().contains("selectById");
            }
            return insertFilter || updateByIdFilter || selectByIdFilter;
        };
    }

    @Bean
    public IdentifierGenerator identifierGenerator() {
        final String key = "workerIdGenerator";
        Long tempWorkerId = redisTemplate.opsForValue().increment(key);
        if (ObjectUtils.isNull(tempWorkerId)) {
            throw new StriveException("获取WorkderID失败");
        }
        long workerId = tempWorkerId % 32L;
        long datacenterId = 0L;
        log.debug("ID生成器参数，workerId:{}, datacenterId:{}", workerId, datacenterId);
        return new DefaultIdentifierGenerator(workerId, datacenterId);
    }
}
