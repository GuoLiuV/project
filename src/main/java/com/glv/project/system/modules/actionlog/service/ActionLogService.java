package com.glv.project.system.modules.actionlog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.project.system.modules.actionlog.dto.ActionLogQueryDto;
import com.glv.project.system.modules.actionlog.entity.ActionLogEntity;
import com.glv.project.system.modules.request.dto.PageRequest;
import com.glv.project.system.modules.response.dto.PageData;

/**
 * @author ZHOUXIANG
 */
public interface ActionLogService extends IService<ActionLogEntity> {

    /**
     * 保存用户行为日志
     *
     * @param actionLogEntity 日志实体
     */
    void saveLog(ActionLogEntity actionLogEntity);

    /**
     * 分页查询用户行为日志
     *
     * @param pageRequest 请求对象
     * @return 分页数据
     */
    PageData<ActionLogEntity> getLogList(
            PageRequest<ActionLogQueryDto> pageRequest);

    /**
     * 删除日志
     *
     * @param unit 时间单位
     */
    void deleteBatch(String unit);

}
