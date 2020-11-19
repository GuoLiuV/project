package com.glv.music.system.modules.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.music.system.enums.AuthTypeEnum;
import com.glv.music.system.modules.exception.StriveException;
import com.glv.music.system.modules.mybatisplus.utils.MyBatisPlusUtils;
import com.glv.music.system.modules.rbac.dao.RbacAuthMapper;
import com.glv.music.system.modules.rbac.dto.RbacAuthDto;
import com.glv.music.system.modules.rbac.entity.RbacAuthEntity;
import com.glv.music.system.modules.rbac.entity.RbacUserEntity;
import com.glv.music.system.modules.rbac.service.RbacAuthService;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import com.glv.music.system.modules.sysadmin.SysAdminUtils;
import com.glv.music.system.utils.BeanUtils;
import com.glv.music.system.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Service
@Slf4j
public class RbacAuthServiceImpl
        extends ServiceImpl<RbacAuthMapper, RbacAuthEntity>
        implements RbacAuthService {

    @Override
    public void save(@Valid RbacAuthDto dto) {
        RbacAuthEntity entity = this.getByCodeNotId(
                dto.getAuthCode(), dto.getId());
        if (ObjectUtils.notNull(entity)) {
            throw new StriveException("该权限代码已经存在");
        }
        dto.setAuthTypeName(AuthTypeEnum
                .getAuthTypeNameByCode(dto.getAuthTypeCode()));
        entity = new RbacAuthEntity();
        BeanUtils.copyPropertiesIgnoreNull(dto, entity);
        this.saveOrUpdate(entity);
    }

    @Override
    public RbacAuthEntity getByCodeNotId(String code, Long id) {
        QueryWrapper<RbacAuthEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("auth_code", code);
        if (ObjectUtils.notNull(id)) {
            queryWrapper.ne("id", id);
        }
        return this.getOne(queryWrapper);
    }

    @Override
    public PageData<RbacAuthEntity> listByPage(PageRequest<RbacAuthDto> pageRequest) {
        QueryWrapper<RbacAuthEntity> queryWrapper = new QueryWrapper<>();
        MyBatisPlusUtils.buildQuery(queryWrapper, pageRequest, "authCode");
        IPage<RbacAuthEntity> page = pageRequest.buildMybatisPlusPage();
        IPage<RbacAuthEntity> iPage = this.page(page, queryWrapper);
        return new PageData<>(iPage);
    }

    @Override
    public void deleteById(RbacAuthDto dto) {
        this.removeById(dto.getId());
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public HashSet<String> getUserAuths(Long userId) {
        if (ObjectUtils.notNull(userId)) {
            return this.baseMapper.getUserAuths(userId);
        } else {
            RbacUserEntity userEntity = SysAdminUtils.getCurrentUserEntity();
            if (ObjectUtils.notNull(userEntity)) {
                return this.baseMapper.getUserAuths(userEntity.getId());
            }
        }
        return new HashSet<>();
    }

    @Override
    public Boolean userHasAuth(Long userId, String authCode) {
        Boolean has = false;
        if (ObjectUtils.notNull(userId)) {
            has = this.baseMapper.userHasAuth(userId, authCode);
        } else {
            RbacUserEntity userEntity = SysAdminUtils.getCurrentUserEntity();
            if (ObjectUtils.notNull(userEntity)) {
                has = this.baseMapper.userHasAuth(userEntity.getId(), authCode);
            }
        }
        return has == null? false : has;
    }
}
