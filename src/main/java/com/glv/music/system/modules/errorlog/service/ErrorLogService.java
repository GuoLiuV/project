package com.glv.music.system.modules.errorlog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.music.system.modules.errorlog.dto.ErrorLogQueryDto;
import com.glv.music.system.modules.errorlog.entity.ErrorLogEntity;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;

/**
 * @author ZHOUXIANG
 */
public interface ErrorLogService extends IService<ErrorLogEntity> {

    /**
     * 记录错误日志
     * @param e 异常信息
     * @param path 请求路径
     */
    void logError(Exception e, String path);

    /**
     * 分页查询错误日志
     * @param pageRequest 请求接口参数
     * @return 分页数据
     */
    PageData<ErrorLogEntity> getLogList(
            PageRequest<ErrorLogQueryDto> pageRequest);

    /**
     * 删除日志
     *
     * @param unit 时间单位
     */
    void deleteBatch(String unit);

}
