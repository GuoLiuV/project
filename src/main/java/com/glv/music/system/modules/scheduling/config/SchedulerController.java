package com.glv.music.system.modules.scheduling.config;

import com.glv.music.system.modules.redis.service.RedisService;
import com.glv.music.system.modules.request.annotation.RestRequestMapping;
import com.glv.music.system.modules.response.dto.ResponseStatusDto;
import com.glv.music.system.modules.scheduling.enums.JobStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 调度器监控管理
 *
 * @author ZHOUXIANG
 */
@Controller
@RequestMapping("/admin/sys/sched/adm")
@Api
public class SchedulerController {

    @Resource
    private RedisService redisService;

    @ApiOperation("判断调度器是否运行,正常返回RUNNING，停止返回STOP")
    @RestRequestMapping("/running")
    public ResponseStatusDto<Object> isSchedRunning() {
        return ResponseStatusDto.success(
                redisService.getOrDefault(
                        "monitor:scheduler", JobStatusEnum.STOP.name())
        );
    }
}
