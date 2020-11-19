package com.glv.music.system.modules.scheduling.job.impl;

import com.glv.music.system.modules.exception.StriveException;
import com.glv.music.system.modules.response.dto.ResponseStatusDto;
import com.glv.music.system.modules.scheduling.job.IStriveJob;
import com.glv.music.system.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZHOUXIANG
 */
@Slf4j
public class StriveTestJob implements IStriveJob {

    @Override
    public ResponseStatusDto<String> handler(String params) {
        log.info("StriveTestJob已执行。。。：{}", params);
        if (StringUtils.isBlank(params)) {
            throw new StriveException("作业参数为空");
        }
        try {
            Thread.sleep(10000);
        }  catch (InterruptedException e) {
            log.info("延时中断");
        }
        return ResponseStatusDto.success();
    }
}
