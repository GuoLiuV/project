package com.glv.project.system.modules.scheduling.config;


import com.caucho.hessian.client.HessianProxyFactory;
import com.glv.project.system.modules.lock.service.LockService;
import com.glv.project.system.modules.property.StriveProperties;
import com.glv.project.system.modules.scheduling.service.StriveSchedulerService;
import com.glv.project.system.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Spring内置定时任务配置类，服务一启动，定时任务就会开始工作
 * 定时任务其实也是一个异步工作的任务
 * 这是spring内置调度
 * # 不启用spring自带的调度 @EnableScheduling
 *
 * @author ZHOUXIANG
 */
@Configuration
@Slf4j
public class SchedulingConfig implements InitializingBean, DisposableBean {

    @Resource
    private StriveProperties striveProperties;

    @Resource
    private StriveSchedulerService striveSchedulerService;

    @Resource
    private LockService lockService;

    private Future<Integer> future;

    private final String lockKey = "lock:sched_lock";

    @Bean
    public HessianProxyFactory hessianProxyFactory() {
        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setUser("strive_hessian");
        factory.setPassword("bf752b221e7ad8e1ccb5574a60e696f1c548b67de05fedd173a4d518");
        return factory;
    }

    @Bean
    public AtomicBoolean schStart() {
        return new AtomicBoolean(false);
    }

    @Override
    public void afterPropertiesSet() {
        if (striveProperties.isSchedEnabled()) {
            // 启动调度器
            this.schStart().set(true);
            // 运行调度器
            future = striveSchedulerService.scheduling(lockKey);
            log.info("调度器成功运行...");
        }
    }

    @Override
    public void destroy() {
        if (ObjectUtils.notNull(future)) {
            future.cancel(false);
            lockService.unlock(lockKey);
        }
    }
}
