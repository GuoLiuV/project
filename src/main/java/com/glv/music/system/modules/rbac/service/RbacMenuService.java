package com.glv.music.system.modules.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.music.system.modules.rbac.dto.RbacMenuDto;
import com.glv.music.system.modules.rbac.entity.RbacMenuEntity;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface RbacMenuService extends IService<RbacMenuEntity> {

    /**
     * 保存菜单信息
     * @param dto 菜单信息
     */
    void save(@Valid RbacMenuDto dto);

    /**
     * 根据代码查询ID不是本身的菜单
     * @param code 代码
     * @param id id
     * @return 菜单信息
     */
    RbacMenuEntity getByCodeNotId(String code, Long id);

    /**
     * 分页查询菜单信息
     * @param pageRequest 分页请求
     * @return 分页结果
     */
    PageData<RbacMenuEntity> list(PageRequest<RbacMenuDto> pageRequest);

    /**
     * 根据ID删除
     * @param dto id字段
     */
    void delete(RbacMenuDto dto);

    /**
     * 批量删除
     * @param ids 所有要删除的ID
     */
    void deleteBatch(List<Long> ids);
}
