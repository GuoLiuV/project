package com.glv.music.system.modules.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.music.system.modules.exception.StriveException;
import com.glv.music.system.modules.mybatisplus.utils.MyBatisPlusUtils;
import com.glv.music.system.modules.rbac.dao.RbacMenuMapper;
import com.glv.music.system.modules.rbac.dto.RbacMenuDto;
import com.glv.music.system.modules.rbac.entity.RbacMenuEntity;
import com.glv.music.system.modules.rbac.service.RbacMenuService;
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
public class RbacMenuServiceImpl
        extends ServiceImpl<RbacMenuMapper, RbacMenuEntity>
        implements RbacMenuService {

    @Override
    public void save(@Valid RbacMenuDto dto) {
        RbacMenuEntity entity = this.getByCodeNotId(
                dto.getMenuCode(), dto.getId());
        if (ObjectUtils.notNull(entity)) {
            throw new StriveException("该菜单限代码已经存在");
        }
        entity = new RbacMenuEntity();
        BeanUtils.copyPropertiesIgnoreNull(dto, entity);
        this.saveOrUpdate(entity);
    }

    @Override
    public RbacMenuEntity getByCodeNotId(String code, Long id) {
        QueryWrapper<RbacMenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_code", code);
        if (ObjectUtils.notNull(id)) {
            queryWrapper.ne("id", id);
        }
        return this.getOne(queryWrapper);
    }

    @Override
    public PageData<RbacMenuEntity> list(PageRequest<RbacMenuDto> pageRequest) {
        QueryWrapper<RbacMenuEntity> queryWrapper = new QueryWrapper<>();
        MyBatisPlusUtils.buildQuery(queryWrapper, pageRequest, "authCode");
        IPage<RbacMenuEntity> page = pageRequest.buildMybatisPlusPage();
        IPage<RbacMenuEntity> iPage = this.page(page, queryWrapper);
        return new PageData<>(iPage);
    }

    @Override
    public void delete(RbacMenuDto dto) {
        this.removeById(dto.getId());
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        this.removeByIds(ids);
    }
}
