package com.glv.project.system.modules.scheduling.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.project.system.modules.request.dto.PageRequest;
import com.glv.project.system.modules.response.dto.PageData;
import com.glv.project.system.modules.scheduling.dto.StriveSchJobDto;
import com.glv.project.system.modules.scheduling.entity.StriveSchJobEntity;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface StriveSchJobService extends IService<StriveSchJobEntity> {

    /**
     * 保存作业
     *
     * @param dto 作业
     */
    void save(@Valid StriveSchJobDto dto);

    /**
     * 根据编号查询作业
     *
     * @param code 编号
     * @param id   id
     * @return 作业
     */
    StriveSchJobEntity getByCodeAndNotId(String code, Long id);

    /**
     * 删除作业
     *
     * @param id id
     */
    void deleteJob(Long id);

    /**
     * 分页查询作业
     *
     * @param pageRequest 分页条件
     * @return 分页结果
     */
    PageData<StriveSchJobEntity> list(
            PageRequest<StriveSchJobDto> pageRequest);

    /**
     * 启动作业，更新内存
     *
     * @param id 作业ID
     */
    void startJob(Long id);

    /**
     * 停止作业，更新内存
     *
     * @param id 作业ID
     */
    void stopJob(Long id);

    /**
     * 执行一次
     *
     * @param id 作业ID
     */
    void runJobOnce(Long id);

    /**
     * 列出所有可执行Job
     *
     * @param handler 处理器
     */
    void listRunningJob(ResultHandler<StriveSchJobEntity> handler);

    /**
     * 初始化任务下一次执行时间
     */
    void initJobNextTime();

    /**
     * 判断执行器是否有作业
     *
     * @param execId 执行器ID
     * @return true/false
     */
    boolean executorHasJob(Long execId);

    /**
     * 获取远程作业端点地址
     *
     * @param entity entity
     * @return url
     */
    String getEndPointUrl(StriveSchJobEntity entity);

    /**
     * 更新下次执行时间
     *
     * @param entity 作业
     */
    void updateNextTime(StriveSchJobEntity entity);
}
