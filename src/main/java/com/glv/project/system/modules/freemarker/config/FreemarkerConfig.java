package com.glv.project.system.modules.freemarker.config;

import com.glv.project.system.modules.freemarker.tags.StriveFtlCommonTag;
import com.glv.project.system.modules.freemarker.tags.StriveFtlJsLibTab;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author ZHOUXIANG
 */
@Configuration
public class FreemarkerConfig implements InitializingBean {

    @Resource
    private freemarker.template.Configuration configuration;

    @Resource
    private StriveFtlCommonTag striveFtlCommonTag;

    @Resource
    private StriveFtlJsLibTab striveFtlJsLibTab;

    @Override
    public void afterPropertiesSet() throws Exception {
        configuration.setSharedVariable("commonTags", striveFtlCommonTag);
        configuration.setSharedVariable("jsLib", striveFtlJsLibTab);
    }
}
