package com.glv.project.system.modules.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.project.system.modules.exception.StriveException;
import com.glv.project.system.modules.mybatisplus.utils.MyBatisPlusUtils;
import com.glv.project.system.modules.rbac.dao.RbacRoleMapper;
import com.glv.project.system.modules.rbac.dto.RbacRoleDto;
import com.glv.project.system.modules.rbac.entity.RbacRoleEntity;
import com.glv.project.system.modules.rbac.service.RbacRoleService;
import com.glv.project.system.modules.request.dto.PageRequest;
import com.glv.project.system.modules.response.dto.PageData;
import com.glv.project.system.utils.BeanUtils;
import com.glv.project.system.utils.CollectionUtils;
import com.glv.project.system.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Slf4j
@Service
public class RbacRoleServiceImpl
        extends ServiceImpl<RbacRoleMapper, RbacRoleEntity> implements RbacRoleService {

    @Override
    public void saveRole(@Valid RbacRoleDto rbacRoleDto) {
        RbacRoleEntity rbacRoleEntity = this.getByCodeAndNotId(
                rbacRoleDto.getRoleCode(), rbacRoleDto.getId());
        if (ObjectUtils.notNull(rbacRoleEntity)) {
            throw new StriveException("该角色代码已存在");
        } else {
            rbacRoleEntity = new RbacRoleEntity();
        }
        BeanUtils.copyPropertiesIgnoreNull(rbacRoleDto, rbacRoleEntity);
        this.saveOrUpdate(rbacRoleEntity);
    }

    @Override
    public RbacRoleEntity getByCodeAndNotId(String roleCode, Long id) {
        QueryWrapper<RbacRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_code", roleCode);
        if (ObjectUtils.notNull(id)) {
            queryWrapper.ne("id", id);
        }
        return this.getOne(queryWrapper);
    }

    @Override
    public PageData<RbacRoleEntity> getRoleList(PageRequest<RbacRoleDto> pageRequest) {
        RbacRoleDto rbacRoleDto = pageRequest.getCondition();
        QueryWrapper<RbacRoleEntity> queryWrapper = new QueryWrapper<>();
        MyBatisPlusUtils.buildQuery(queryWrapper, rbacRoleDto,"roleCode");
        MyBatisPlusUtils.buildSortOrderQuery(queryWrapper, pageRequest.getSort());
        Page<RbacRoleEntity> page = pageRequest.buildMybatisPlusPage();
        IPage<RbacRoleEntity> iPage = this.page(page, queryWrapper);
        return new PageData<>(iPage);
    }

    @Override
    public void deleteRole(RbacRoleDto rbacRoleDto) {
        if (ObjectUtils.isNull(rbacRoleDto.getId())) {
            throw new StriveException("要删除的角色ID为空");
        }
        this.removeById(rbacRoleDto.getId());
    }

    @Override
    public void deleteBatchRole(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new StriveException("要删除的角色ID为空");
        }
        this.removeByIds(ids);
    }
}
