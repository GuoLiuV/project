package com.glv.music.system.modules.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.music.system.modules.exception.StriveException;
import com.glv.music.system.modules.mybatisplus.utils.MyBatisPlusUtils;
import com.glv.music.system.modules.rbac.dao.RbacElemMapper;
import com.glv.music.system.modules.rbac.dto.RbacElemDto;
import com.glv.music.system.modules.rbac.entity.RbacElemEntity;
import com.glv.music.system.modules.rbac.service.RbacElemService;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import com.glv.music.system.utils.BeanUtils;
import com.glv.music.system.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Service
@Slf4j
public class RbacElemServiceImpl
        extends ServiceImpl<RbacElemMapper, RbacElemEntity>
        implements RbacElemService {

    @Override
    public void save(@Valid RbacElemDto dto) {
        RbacElemEntity entity = this.getByCodeNotId(
                dto.getElemCode(), dto.getId());
        if (ObjectUtils.notNull(entity)) {
            throw new StriveException("该元素代码已经存在");
        }
        entity = new RbacElemEntity();
        BeanUtils.copyPropertiesIgnoreNull(dto, entity);
        this.saveOrUpdate(entity);
    }

    @Override
    public RbacElemEntity getByCodeNotId(String code, Long id) {
        QueryWrapper<RbacElemEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("elem_code", code);
        if (ObjectUtils.notNull(id)) {
            queryWrapper.ne("id", id);
        }
        return this.getOne(queryWrapper);
    }

    @Override
    public PageData<RbacElemEntity> list(PageRequest<RbacElemDto> pageRequest) {
        QueryWrapper<RbacElemEntity> queryWrapper = new QueryWrapper<>();
        MyBatisPlusUtils.buildQuery(queryWrapper, pageRequest, "authCode");
        IPage<RbacElemEntity> page = pageRequest.buildMybatisPlusPage();
        IPage<RbacElemEntity> iPage = this.page(page, queryWrapper);
        return new PageData<>(iPage);
    }

    @Override
    public void delete(RbacElemDto dto) {
        this.removeById(dto.getId());
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        this.removeByIds(ids);
    }
}
