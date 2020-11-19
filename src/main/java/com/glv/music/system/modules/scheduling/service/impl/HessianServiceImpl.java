package com.glv.music.system.modules.scheduling.service.impl;

import com.caucho.hessian.client.HessianProxyFactory;
import com.glv.music.system.modules.exception.StriveException;
import com.glv.music.system.modules.mail.service.MailService;
import com.glv.music.system.modules.response.dto.ResponseStatusDto;
import com.glv.music.system.modules.scheduling.dto.StriveSchRecordDto;
import com.glv.music.system.modules.scheduling.entity.StriveSchJobEntity;
import com.glv.music.system.modules.scheduling.entity.StriveSchRecordEntity;
import com.glv.music.system.modules.scheduling.enums.JobStatusEnum;
import com.glv.music.system.modules.scheduling.job.IStriveJob;
import com.glv.music.system.modules.scheduling.service.HessianService;
import com.glv.music.system.modules.scheduling.service.StriveSchJobService;
import com.glv.music.system.modules.scheduling.service.StriveSchRecordService;
import com.glv.music.system.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.MalformedURLException;

/**
 * Hession远程过程调度服务
 *
 * @author ZHOUXIANG
 */
@Service
@Slf4j
public class HessianServiceImpl implements HessianService {

    @Resource
    private HessianProxyFactory hessianProxyFactory;

    @Resource
    private StriveSchRecordService striveSchRecordService;

    @Resource
    @Lazy
    private StriveSchJobService striveSchJobService;

    @Resource
    private MailService mailService;

    @Override
    public IStriveJob getStriveJob(String url) throws MalformedURLException {
        return (IStriveJob) hessianProxyFactory
                .create(IStriveJob.class, url);
    }

    @Override
    public void executeJob(StriveSchJobEntity job) {
        // 保存作业执行实例
        Long instanceId = this.saveJobRecord(job);
        try {
            // 获取作业实例
            IStriveJob instance = this.getStriveJob(
                    striveSchJobService.getEndPointUrl(job));
            // 执行作业
            ResponseStatusDto<String> res = instance.handler(job.getParams());
            if (res.isSuccess()) {
                // 更新作业执行记录
                striveSchRecordService.updateStatus(
                        instanceId, JobStatusEnum.SUCCESS.name());
            } else {
                throw new StriveException(res.getMessage());
            }
        } catch (Exception e) {
            // 更行执行记录异常信息
            striveSchRecordService.updateStackTrace(instanceId, e);
            // 发送邮件通知
            this.sendMail(instanceId, job.getEmail());
            // 抛出异常，判断重试
            throw new StriveException(e.getMessage());
        }
    }

    /**
     * 保存作业执行实例记录
     *
     * @param job 作业
     */
    private Long saveJobRecord(StriveSchJobEntity job) {
        StriveSchRecordDto recordDto = new StriveSchRecordDto();
        recordDto.setJobId(job.getId()).setJobName(job.getName())
                .setTenantId(job.getTenantId());
        striveSchRecordService.save(recordDto);
        return recordDto.getId();
    }

    /**
     * 发送邮件通知
     *
     * @param id    执行ID
     * @param email 通知邮件地址
     */
    private void sendMail(Long id, String email) {
        StriveSchRecordEntity record = striveSchRecordService.getById(id);
        if (ObjectUtils.notNull(record)) {
            String content = "<div>" +
                    String.format("<div>作业名称：<div style='font-weight:bold;color:red;'>%s</div></div>", record.getJobName()) +
                    String.format("<div>执行编号：<div style='font-weight:bold;color:red;'>%s</div></div>", record.getId()) +
                    String.format("<div>异常堆栈：<div style='font-weight:bold;color:red;'>%s</div></div>", record.getStackTrace()) +
                    "</ul>";
            mailService.sendHtmlMail("STRIVE 平台任务执行失败", content, email);
        }
    }
}
