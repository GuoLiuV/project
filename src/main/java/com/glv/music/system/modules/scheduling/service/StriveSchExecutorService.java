package com.glv.music.system.modules.scheduling.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import com.glv.music.system.modules.scheduling.dto.StriveSchExecutorDto;
import com.glv.music.system.modules.scheduling.entity.StriveSchExecutorEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface StriveSchExecutorService
        extends IService<StriveSchExecutorEntity> {

    /**
     * 保存执行器（hessian服务端信息）
     *
     * @param dto 执行器
     */
    void save(@Valid StriveSchExecutorDto dto);

    /**
     * 查询code存在，id不存在的执行器记录
     *
     * @param code 执行器编码
     * @param id   ID
     * @return 执行器
     */
    StriveSchExecutorEntity getByCodeAndNotId(String code, Long id);

    /**
     * 根据id查询执行器，忽略租户
     *
     * @param id id
     * @return 执行器
     */
    StriveSchExecutorEntity getExecutorById(Long id);

    /**
     * 获取服务地址列表
     *
     * @param id 执行器ID
     * @return 地址列表
     */
    List<String> getUrlsById(Long id);

    /**
     * 分页查询
     *
     * @param pageRequest 分页条件
     * @return 分页结果
     */
    PageData<StriveSchExecutorEntity> list(
            PageRequest<StriveSchExecutorDto> pageRequest);

    /**
     * 删除执行器，如果执行器有关联作业，不能删除
     *
     * @param id 执行器ID
     */
    void deleteById(Long id);
}
