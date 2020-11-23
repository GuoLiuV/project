package com.glv.project.system.modules.drools.config;

import org.kie.api.KieBase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZHOUXIANG
 */
@Configuration
public class DroolsConfiguration {

    @Bean
    public ConcurrentHashMap<String, KieBase> kieBaseMap() {
        return new ConcurrentHashMap<>(10);
    }
}
