package com.glv.music.system.modules.scheduling.disruptor;

import com.glv.music.system.modules.scheduling.entity.StriveSchJobEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ZHOUXIANG
 */
@Data
@Accessors(chain = true)
public class SchDisruptorModel {

    /**
     * 作业实体
     */
    private StriveSchJobEntity jobEntity;
}
