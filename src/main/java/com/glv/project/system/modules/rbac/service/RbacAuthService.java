package com.glv.project.system.modules.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.project.system.modules.rbac.dto.RbacAuthDto;
import com.glv.project.system.modules.rbac.entity.RbacAuthEntity;
import com.glv.project.system.modules.request.dto.PageRequest;
import com.glv.project.system.modules.response.dto.PageData;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface RbacAuthService extends IService<RbacAuthEntity> {

    /**
     * 保存权限信息
     *
     * @param dto 权限信息
     */
    void save(@Valid RbacAuthDto dto);

    /**
     * 根据代码查询ID不是本身的权限
     *
     * @param code 代码
     * @param id   id
     * @return 权限信息
     */
    RbacAuthEntity getByCodeNotId(String code, Long id);

    /**
     * 分页查询权限信息
     *
     * @param pageRequest 分页请求
     * @return 分页结果
     */
    PageData<RbacAuthEntity> listByPage(PageRequest<RbacAuthDto> pageRequest);

    /**
     * 根据ID删除
     *
     * @param dto id字段
     */
    void deleteById(RbacAuthDto dto);

    /**
     * 批量删除
     *
     * @param ids 所有要删除的ID
     */
    void deleteBatch(List<Long> ids);

    /**
     * 获取用户权限
     *
     * @param userId 用户ID
     * @return 权限代码集合
     */
    HashSet<String> getUserAuths(Long userId);

    /**
     * 判断用户是否具有某权限
     *
     * @param userId   用户ID
     * @param authCode 权限代码
     * @return 是否具有
     */
    Boolean userHasAuth(Long userId, String authCode);
}
