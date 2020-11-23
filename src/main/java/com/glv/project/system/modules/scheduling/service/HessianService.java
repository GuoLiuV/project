package com.glv.project.system.modules.scheduling.service;

import com.glv.project.system.modules.scheduling.entity.StriveSchJobEntity;
import com.glv.project.system.modules.scheduling.job.IStriveJob;
import org.springframework.validation.annotation.Validated;

import java.net.MalformedURLException;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface HessianService {

    /**
     * 远程过程调用获取调用实例
     *
     * @param url 远程地址
     * @return 作业实例
     * @throws MalformedURLException 异常
     */
    IStriveJob getStriveJob(String url) throws MalformedURLException;

    /**
     * 执行作业
     *
     * @param job 作业
     */
    void executeJob(StriveSchJobEntity job);
}
