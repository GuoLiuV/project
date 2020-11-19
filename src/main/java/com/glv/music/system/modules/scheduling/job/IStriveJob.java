package com.glv.music.system.modules.scheduling.job;

import com.glv.music.system.modules.response.dto.ResponseStatusDto;

/**
 * @author ZHOUXIANG
 */
public interface IStriveJob {

    /**
     * 作业处理器方法
     *
     * @param params 作业调度传参
     * @return 调用结果
     */
    ResponseStatusDto<String> handler(String params);
}
