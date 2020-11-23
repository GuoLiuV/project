package com.glv.project.system.modules.scheduling.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.project.system.modules.request.dto.PageRequest;
import com.glv.project.system.modules.response.dto.PageData;
import com.glv.project.system.modules.scheduling.dto.StriveSchRecordDto;
import com.glv.project.system.modules.scheduling.entity.StriveSchRecordEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface StriveSchRecordService
        extends IService<StriveSchRecordEntity> {

    /**
     * 保存作业执行实例
     *
     * @param dto 作业实例
     */
    void save(@Valid StriveSchRecordDto dto);

    /**
     * 分页查询
     *
     * @param pageRequest 条件
     * @return 结果
     */
    PageData<StriveSchRecordEntity> list(
            PageRequest<StriveSchRecordDto> pageRequest);

    /**
     * 保存异常堆栈信息
     *
     * @param id 作业实例ID
     * @param e  异常信息
     */
    void updateStackTrace(Long id, Exception e);

    /**
     * 保存作业实例运行状态，和完成时间
     *
     * @param id     id
     * @param status 状态
     */
    void updateStatus(Long id, String status);

    /**
     * 清除1日/1周/1月之前的日志或所有日志
     *
     * @param unit 单位，day,week,month,all
     */
    void deleteBatch(String unit);
}
