package com.glv.music.system.modules.scheduling.disruptor;

import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

/**
 * @author ZHOUXIANG
 */
@Component
public class SchDisruptorEventFactory implements EventFactory<SchDisruptorModel> {

    @Override
    public SchDisruptorModel newInstance() {
        return new SchDisruptorModel();
    }
}
