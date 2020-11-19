package com.glv.music.system.modules.accesslog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.music.system.modules.accesslog.dto.AccessLogQueryDto;
import com.glv.music.system.modules.accesslog.entity.AccessLogEntity;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;

/**
 * @author ZHOUXIANG
 */
public interface AccessLogService extends IService<AccessLogEntity> {

    /**
     * 保存访问日志
     *
     * @param accessLogEntity 访问日志实体
     */
    void saveLog(AccessLogEntity accessLogEntity);

    /**
     * 分页查询访问日志
     *
     * @param pageRequest 查询请求接口
     * @return 分页数据
     */
    PageData<AccessLogEntity> getLogList(
            PageRequest<AccessLogQueryDto> pageRequest);

    /**
     * 删除日志
     *
     * @param unit 时间单位
     */
    void deleteBatch(String unit);

}
