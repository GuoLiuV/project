package com.glv.music.system.modules.thymeleaf.config;

import com.glv.music.system.modules.thymeleaf.StriveDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

/**
 * Thymeleaf模板配置
 *
 * @author ZHOUXIANG
 */
@Configuration
public class ThymeleafConfig {

    /**
     * 配置模型引擎
     */
    @Bean
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver springResourceTemplateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setEnableSpringELCompiler(true);
        // 添加thymeleaf视图解析组件
        templateEngine.addTemplateResolver(springResourceTemplateResolver);
        //添加SpringSecurity方言
        templateEngine.addDialect(new SpringSecurityDialect());
        //添加自定义方言
        templateEngine.addDialect(new StriveDialect());
        return templateEngine;
    }

    /**
     * 配置视图解析器
     */
    @Bean
    public ThymeleafViewResolver viewResolver(SpringTemplateEngine springTemplateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(springTemplateEngine);
        return viewResolver;
    }

}
