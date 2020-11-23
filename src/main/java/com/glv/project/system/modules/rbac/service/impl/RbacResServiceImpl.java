package com.glv.project.system.modules.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.project.system.modules.exception.StriveException;
import com.glv.project.system.modules.mybatisplus.utils.MyBatisPlusUtils;
import com.glv.project.system.modules.rbac.dao.RbacResMapper;
import com.glv.project.system.modules.rbac.dto.RbacResDto;
import com.glv.project.system.modules.rbac.entity.RbacResEntity;
import com.glv.project.system.modules.rbac.service.RbacResService;
import com.glv.project.system.modules.request.dto.PageRequest;
import com.glv.project.system.modules.response.dto.PageData;
import com.glv.project.system.utils.BeanUtils;
import com.glv.project.system.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Service
@Slf4j
public class RbacResServiceImpl
        extends ServiceImpl<RbacResMapper, RbacResEntity>
        implements RbacResService {

    @Override
    public void save(@Valid RbacResDto dto) {
        RbacResEntity entity = this.getByCodeNotId(
                dto.getResUri(), dto.getId());
        if (ObjectUtils.notNull(entity)) {
            throw new StriveException("该资源代码已经存在");
        }
        entity = new RbacResEntity();
        BeanUtils.copyPropertiesIgnoreNull(dto, entity);
        this.saveOrUpdate(entity);
    }

    @Override
    public RbacResEntity getByCodeNotId(String code, Long id) {
        QueryWrapper<RbacResEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("res_uri", code);
        if (ObjectUtils.notNull(id)) {
            queryWrapper.ne("id", id);
        }
        return this.getOne(queryWrapper);
    }

    @Override
    public PageData<RbacResEntity> list(PageRequest<RbacResDto> pageRequest) {
        QueryWrapper<RbacResEntity> queryWrapper = new QueryWrapper<>();
        MyBatisPlusUtils.buildQuery(queryWrapper, pageRequest);
        IPage<RbacResEntity> page = pageRequest.buildMybatisPlusPage();
        IPage<RbacResEntity> iPage = this.page(page, queryWrapper);
        return new PageData<>(iPage);
    }

    @Override
    public void delete(RbacResDto dto) {
        this.removeById(dto.getId());
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        this.removeByIds(ids);
    }
}
