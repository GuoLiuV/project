package com.glv.project.system.modules.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.glv.project.system.modules.accesslog.interceptor.AccessLogInterceptor;
import com.glv.project.system.modules.web.interceptor.UserAgentInterceptor;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.OAuth2ClientConfiguration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

/**
 * @author ZHOUXIANG
 */
@Data
@EnableAsync
@Configuration
public class WebAppConfig implements WebMvcConfigurer, ApplicationContextAware, ErrorPageRegistrar {

    private ApplicationContext applicationContext;

    /**
     * 设置Spring 上下文管理器
     */
    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * http请求
     */
    @Bean
    @Qualifier("restTemplate")
    public RestTemplate commonRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    /**
     * 带oauth2 token的请求
     */
    @Bean
    @Qualifier("oauth2RestTemplate")
    @ConditionalOnBean(OAuth2ClientConfiguration.class)
    public OAuth2RestTemplate oAuth2RestTemplate(
            OAuth2ProtectedResourceDetails resource,
            OAuth2ClientContext oAuth2ClientContext) {
        return new OAuth2RestTemplate(resource, oAuth2ClientContext);
    }

    /**
     * 添加无逻辑的视图
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //示例页面
        registry.addViewController("/example_th").setViewName("example_th");
        registry.addViewController("/example_ftl").setViewName("example_ftl");
        //开放的首页请求路径
        registry.addViewController("/").setViewName("user/index");
        registry.addViewController("/index").setViewName("user/index");
        //受权访问的首页请求路径
        registry.addViewController("/admin").setViewName("admin/index");
        registry.addViewController("/admin/index").setViewName("admin/index");
        //登录页面请求路径
        registry.addViewController("/login_page").setViewName("login");
        //配置错误页面
        registry.addViewController("/e404").setViewName("e404");
        registry.addViewController("/e500").setViewName("e500");
    }

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 浏览器版本检测拦截器
        registry.addInterceptor(new UserAgentInterceptor()).addPathPatterns("/**");
        // 用户访问日志
        registry.addInterceptor(new AccessLogInterceptor())
                .excludePathPatterns("/sys/**", "/app/**").addPathPatterns("/**");
    }

    /**
     * 配置JDBC Template访问数据库的方式
     */
    @Bean
    public JdbcTemplate primaryJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * 配置系统错误页面
     */
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/e500"));
        registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/e404"));
    }

    /**
     * 添加跨域访问限制
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    /**
     * 构建objectmapper，主要是添加long转string序列化器，主要是为了分布式
     *
     * @param builder 构建器
     * @return ObjectMapper
     */
    @Bean
    @Primary
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Integer.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Short.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Double.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Float.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }
}
