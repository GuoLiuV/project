package com.glv.music.system.modules.jpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 自定义JdbcTemplate配置，使用指定的数据源
 *
 * @author ZHOUXIANG
 */
@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.glv.music.**.repo")
@EntityScan(basePackages = "com.glv.music.**.entity")
public class JpaConfig {
}
