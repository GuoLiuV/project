package com.glv.project.system.modules.scheduling.service;

import com.glv.project.system.modules.scheduling.entity.StriveSchJobEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;

import java.util.concurrent.Future;

/**
 * 作业调度服务
 *
 * @author ZHOUXIANG
 */
@Validated
public interface StriveSchedulerService {

    /**
     * 调度线程
     *
     * @param lockKey 锁键名称
     * @return 线程是否结束
     */
    @Async
    Future<Integer> scheduling(String lockKey);

    /**
     * 判断作业是否可执行，时间已到，且状态是正在运行
     *
     * @param entity 作业
     * @return true/false
     */
    boolean canExecute(StriveSchJobEntity entity);
}
