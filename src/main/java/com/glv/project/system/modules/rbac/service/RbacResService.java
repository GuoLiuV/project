package com.glv.project.system.modules.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.project.system.modules.rbac.dto.RbacResDto;
import com.glv.project.system.modules.rbac.entity.RbacResEntity;
import com.glv.project.system.modules.request.dto.PageRequest;
import com.glv.project.system.modules.response.dto.PageData;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface RbacResService extends IService<RbacResEntity> {

    /**
     * 保存资源信息
     * @param dto 资源信息
     */
    void save(@Valid RbacResDto dto);

    /**
     * 根据代码查询ID不是本身的资源
     * @param code 代码
     * @param id id
     * @return 资源信息
     */
    RbacResEntity getByCodeNotId(String code, Long id);

    /**
     * 分页查询资源信息
     * @param pageRequest 分页请求
     * @return 分页结果
     */
    PageData<RbacResEntity> list(PageRequest<RbacResDto> pageRequest);

    /**
     * 根据ID删除
     * @param dto id字段
     */
    void delete(RbacResDto dto);

    /**
     * 批量删除
     * @param ids 所有要删除的ID
     */
    void deleteBatch(List<Long> ids);
}
