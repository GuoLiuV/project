package com.glv.project.system.modules.scheduling.job.exporter;

import com.glv.project.system.modules.scheduling.job.impl.StriveTestJob;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZHOUXIANG
 */
@Configuration
public class JobExporter {

    @Bean("/job/exporter/striveTestJob")
    public StriveHessianServiceExporter testJob() {
        return new StriveHessianServiceExporter(new StriveTestJob());
    }
}
