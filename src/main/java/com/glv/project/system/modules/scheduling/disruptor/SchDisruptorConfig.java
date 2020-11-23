package com.glv.project.system.modules.scheduling.disruptor;

import com.glv.project.system.modules.property.StriveProperties;
import com.glv.project.system.modules.scheduling.entity.StriveSchJobEntity;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.Executors;

/**
 * @author ZHOUXIANG
 */
@Configuration
@Slf4j
public class SchDisruptorConfig {

    @Resource
    private SchDisruptorEventFactory schDisruptorEventFactory;

    @Resource
    private StriveProperties striveProperties;

    @Bean
    public RingBuffer<SchDisruptorModel> schRingBuffer() {
        final int bufferSize = 1024, handleCount = striveProperties.getSchedThreadCount();
        Disruptor<SchDisruptorModel> disruptor =
                new Disruptor<>(schDisruptorEventFactory, bufferSize,
                        Executors.defaultThreadFactory(),
                        ProducerType.SINGLE, new BlockingWaitStrategy());
        SchDisruptorEventHandler[] handlers = new SchDisruptorEventHandler[handleCount];
        for (int i = 0; i < handleCount; i++) {
            handlers[i] = new SchDisruptorEventHandler();
        }
        disruptor.handleEventsWithWorkerPool(handlers);
        disruptor.start();
        return disruptor.getRingBuffer();
    }

    /**
     * 发布事件
     *
     * @param message 事件数据
     */
    public void publishEvent(StriveSchJobEntity message) {
        RingBuffer<SchDisruptorModel> schRingBuffer = schRingBuffer();
        long sequence = schRingBuffer.next();
        SchDisruptorModel model = schRingBuffer.get(sequence);
        model.setJobEntity(message);
        schRingBuffer.publish(sequence);
    }
}
