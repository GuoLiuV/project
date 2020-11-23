package com.glv.project.system.modules.scheduling.service.impl;

import com.glv.project.system.modules.lock.service.LockService;
import com.glv.project.system.modules.redis.utils.RedisUtils;
import com.glv.project.system.modules.scheduling.disruptor.SchDisruptorConfig;
import com.glv.project.system.modules.scheduling.entity.StriveSchJobEntity;
import com.glv.project.system.modules.scheduling.enums.JobStatusEnum;
import com.glv.project.system.modules.scheduling.service.StriveSchJobService;
import com.glv.project.system.modules.scheduling.service.StriveSchedulerService;
import com.glv.project.system.utils.DateUtils;
import com.glv.project.system.utils.ObjectUtils;
import com.glv.project.system.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 这个类中相关查询都要去掉租户
 *
 * @author ZHOUXIANG
 */
@Service
@Slf4j
public class StriveSchedulerServiceImpl implements StriveSchedulerService {

    @Resource
    @Lazy
    private StriveSchJobService striveSchJobService;

    @Resource
    @Qualifier("schStart")
    private AtomicBoolean start;

    @Resource
    private LockService lockService;

    @Resource
    private SchDisruptorConfig schDisruptorConfig;

    @Override
    @Async
    public Future<Integer> scheduling(String lockKey) {
        // 初始化所有任务的下一次执行时间
        striveSchJobService.initJobNextTime();
        // 循环检测任务是否可执行，并执行。
        while (start.get()) {
            try {
                if (lockService.lock(lockKey, 60)) {
                    // 循环查询job
                    striveSchJobService.listRunningJob(resultContext -> {
                        StriveSchJobEntity entity = resultContext.getResultObject();
                        try {
                            if (this.canExecute(entity)) {
                                schDisruptorConfig.publishEvent(entity);
                                striveSchJobService.updateNextTime(entity);
                            }
                        } catch (Exception e) {
                            log.error("{}-该任务执行异常：{}，",
                                    entity.getName(), e.getMessage());
                        }
                    });
                    // 解锁
                    lockService.unlock(lockKey);
                }
                // 延时
                this.sleep();
                // redis 心跳机制，监控调度器是否正常，放在最后，
                // 只要前面有异常，此处就不执行
                RedisUtils.set("monitor:scheduler",
                        JobStatusEnum.RUNNING.name(), 120);
            } catch (Exception e) {
                log.error("调试停止运行一次: {}", e.getMessage());
            }
        }
        return new AsyncResult<>(0);
    }

    @Override
    public boolean canExecute(StriveSchJobEntity entity) {
        if (StringUtils.equalsIgnoreCase(
                entity.getStatus(), JobStatusEnum.RUNNING.name())) {
            Date nextTime = entity.getNextTime();
            if (ObjectUtils.isNull(nextTime)) {
                // 更新job下一次执行时间
                striveSchJobService.updateNextTime(entity);
            } else {
                LocalDateTime nextLocalDateTime = DateUtils
                        .date2LocalDateTime(nextTime);
                return nextLocalDateTime.isBefore(LocalDateTime.now());
            }
        }
        return false;
    }

    /**
     * 延时
     */
    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.debug("延时异常");
        }
    }
}
