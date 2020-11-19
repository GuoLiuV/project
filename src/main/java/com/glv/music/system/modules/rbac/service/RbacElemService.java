package com.glv.music.system.modules.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.music.system.modules.rbac.dto.RbacElemDto;
import com.glv.music.system.modules.rbac.entity.RbacElemEntity;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface RbacElemService extends IService<RbacElemEntity> {

    /**
     * 保存元素信息
     * @param dto 元素信息
     */
    void save(@Valid RbacElemDto dto);

    /**
     * 根据代码查询ID不是本身的元素
     * @param code 代码
     * @param id id
     * @return 元素信息
     */
    RbacElemEntity getByCodeNotId(String code, Long id);

    /**
     * 分页查询元素信息
     * @param pageRequest 分页请求
     * @return 分页结果
     */
    PageData<RbacElemEntity> list(PageRequest<RbacElemDto> pageRequest);

    /**
     * 根据ID删除
     * @param dto id字段
     */
    void delete(RbacElemDto dto);

    /**
     * 批量删除
     * @param ids 所有要删除的ID
     */
    void deleteBatch(List<Long> ids);
}
