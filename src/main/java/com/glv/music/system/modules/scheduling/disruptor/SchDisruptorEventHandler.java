package com.glv.music.system.modules.scheduling.disruptor;

import com.glv.music.system.modules.scheduling.entity.StriveSchJobEntity;
import com.glv.music.system.modules.scheduling.service.HessianService;
import com.glv.music.system.modules.web.component.SpringContextHolder;
import com.glv.music.system.utils.ObjectUtils;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZHOUXIANG
 */
@Slf4j
public class SchDisruptorEventHandler implements
        EventHandler<SchDisruptorModel>, WorkHandler<SchDisruptorModel> {

    @Override
    public void onEvent(SchDisruptorModel event, long sequence, boolean endOfBatch) {
        this.onEvent(event);
    }

    @Override
    public void onEvent(SchDisruptorModel event) {
        StriveSchJobEntity job = event.getJobEntity();
        if (ObjectUtils.notNull(job)) {
            // 定义重新次数，默认为0，不重试
            Integer retry = job.getRetry();
            if (ObjectUtils.isNull(retry)) {
                retry = 0;
            }
            // 进入重试循环
            do {
                try {
                    HessianService hessianService =
                            SpringContextHolder.getBean(HessianService.class);
                    hessianService.executeJob(event.getJobEntity());
                    // 正常结束，退出循环
                    break;
                } catch (Exception e) {
                    log.info("disruptor 任务执行异常, 并进行重试");
                    retry--;
                }
            } while (retry >= 0);
        }
    }
}
