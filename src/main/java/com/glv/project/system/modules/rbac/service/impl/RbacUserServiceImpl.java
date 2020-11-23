package com.glv.project.system.modules.rbac.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.project.system.enums.OperateStatusEnum;
import com.glv.project.system.enums.OrgTypeEnum;
import com.glv.project.system.enums.SexEnum;
import com.glv.project.system.modules.exception.StriveException;
import com.glv.project.system.modules.mybatisplus.utils.MyBatisPlusUtils;
import com.glv.project.system.modules.rbac.dao.RbacUserMapper;
import com.glv.project.system.modules.rbac.dto.*;
import com.glv.project.system.modules.rbac.entity.RbacUserEntity;
import com.glv.project.system.modules.rbac.service.RbacOrgRoleService;
import com.glv.project.system.modules.rbac.service.RbacOrgService;
import com.glv.project.system.modules.rbac.service.RbacUserRoleService;
import com.glv.project.system.modules.rbac.service.RbacUserService;
import com.glv.project.system.modules.request.dto.PageRequest;
import com.glv.project.system.modules.response.dto.PageData;
import com.glv.project.system.modules.sysadmin.SysAdminUtils;
import com.glv.project.system.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 用户服务类
 *
 * @author ZHOUXIANG
 */
@Slf4j
@Service
public class RbacUserServiceImpl
        extends ServiceImpl<RbacUserMapper, RbacUserEntity> implements RbacUserService {

    @Resource
    @Lazy
    private RbacUserRoleService rbacUserRoleService;

    @Resource
    private RbacOrgRoleService rbacOrgRoleService;

    @Resource
    @Lazy
    private RbacOrgService rbacOrgService;

    @Resource
    private CacheManager cacheManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerUser(@Valid RbacUserRegDto regDto) {
        if (!StringUtils.equals(regDto.getPassword(), regDto.getConfirmPassword())) {
            throw new StriveException("两次密码输入不一致");
        }
        RbacUserEntity entity = new RbacUserEntity();
        // 注册的都是租户
        entity.setIsTenant(OperateStatusEnum.Y.name())
                .setLoginName(regDto.getLoginName())
                .setPassword(DigestUtils.md5Hex(regDto.getPassword()));
        try {
            this.saveOrUpdate(entity);
        } catch (DuplicateKeyException e) {
            throw new StriveException("该用户登录名已存在");
        }
        // 生成租户顶层组织
        RbacOrgDto orgDto = new RbacOrgDto();
        orgDto.setOrgCode(regDto.getOrgCode())
                .setOrgName(regDto.getOrgName())
                .setAliasName(regDto.getOrgAliasName())
                .setOrgTypeCode(OrgTypeEnum.COMPANY.name());
        // 租户ID即为当前注册用户的ID
        rbacOrgService.registerOrg(entity.getId(), orgDto);
    }

    @Override
    @CacheEvict(value = "sys", key = "'loginUser'.concat('-').concat(#rbacUserDto.loginName)")
    public void saveUser(@Valid RbacUserDto rbacUserDto) {
        RbacUserEntity rbacUserEntity = new RbacUserEntity();
        BeanUtils.copyPropertiesIgnoreNull(rbacUserDto, rbacUserEntity);
        // 性别中文处理
        rbacUserEntity.setSexName(
                SexEnum.getValueByName(rbacUserDto.getSexCode()));
        // 如果密码为空且是新增则给一个默认密码
        if (StringUtils.isBlank(rbacUserEntity.getPassword())) {
            if (ObjectUtils.isNull(rbacUserEntity.getId())) {
                rbacUserEntity.setPassword(RandomStringUtils.genUUID());
            }
        } else {
            rbacUserEntity.setPassword(
                    DigestUtils.md5Hex(rbacUserEntity.getPassword()));
        }
        try {
            this.saveOrUpdate(rbacUserEntity);
        } catch (DuplicateKeyException e) {
            throw new StriveException("该用户登录名已存在");
        }
    }

    @Override
    public RbacUserEntity getOneByMobileAndInsert(String mobile) {
        QueryWrapper<RbacUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        RbacUserEntity userEntity;
        try {
            userEntity = this.getOne(queryWrapper);
        } catch (MyBatisSystemException e) {
            throw new StriveException("手机号重复");
        }
        // 如果此手机号的用户不存在则插入
        if (ObjectUtils.isNull(userEntity)) {
            userEntity = new RbacUserEntity();
            userEntity.setLoginName(mobile).setPassword(DigestUtils.md5Hex(mobile.concat("_strive")))
                    .setMobile(mobile).setIsTenant(OperateStatusEnum.N.name());
            this.saveOrUpdate(userEntity);
        }
        return userEntity;
    }

    @Override
    public PageData<RbacUserEntity> getUserList(PageRequest<RbacUserDto> pageRequest) {
        RbacUserDto rbacUserDto = pageRequest.getCondition();
        QueryWrapper<RbacUserEntity> queryWrapper = new QueryWrapper<>();
        MyBatisPlusUtils.buildQuery(queryWrapper, pageRequest);
        MyBatisPlusUtils.buildSortOrderQuery(queryWrapper, pageRequest.getSort());
        Page<RbacUserEntity> page = pageRequest.buildMybatisPlusPage();
        IPage<RbacUserEntity> iPage = this.page(page, queryWrapper);
        return new PageData<>(iPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "sys", key = "'loginUser'.concat('-').concat(#rbacUserDto.loginName)")
    public void deleteUser(RbacUserDto rbacUserDto) {
        if (ObjectUtils.isNull(rbacUserDto.getId())) {
            throw new StriveException("用户ID不能为空");
        }
        this.removeById(rbacUserDto.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatchUser(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new StriveException("请选择用户ID");
        }
        this.removeByIds(ids);
    }

    @Override
    public RbacUserEntity getOneByLoginName(String loginName) {
        QueryWrapper<RbacUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", loginName);
        return this.getOne(queryWrapper);
    }

    @Override
    public Long getTenantIdByUserId(Long userId) {
        RbacUserEntity entity = this.getById(userId);
        if (ObjectUtils.notNull(entity)) {
            if (StringUtils.equalsIgnoreCase(
                    entity.getIsTenant(), OperateStatusEnum.Y.name())) {
                // 当前登录用户是租户自己
                return entity.getId();
            }
            return entity.getTenantId();
        }
        return -1L;
    }

    @Override
    @Cacheable(value = "sys", key = "'loginUser'.concat('-').concat(#loginName)")
    public RbacUserEntity getUserByLoginName(String loginName) {
        QueryWrapper<RbacUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", loginName)
                .last("limit 1");
        return this.baseMapper.getUserByLoginName(queryWrapper);
    }

    @Override
    public void changeUsersOrg(RbacUserOrgDto dto) {
        if (CollectionUtils.isEmpty(dto.getUserIds())) {
            throw new StriveException("用户ID列表为空");
        }
        dto.getUserIds().forEach(
                userId -> updateUserOrg(userId, dto.getOrgId()));
    }

    @Override
    public void changeUsersPassword(@Valid ChangeUserPasswordDto dto) {
        List<Long> userIds = dto.getUserIds();
        if (dto.isReset()) {
            if (CollectionUtils.isEmpty(userIds)) {
                throw new StriveException("用户ID不能为空");
            }
            userIds.forEach(
                    userId -> changeUserPassword(userId, dto.getPassword()));
        } else {
            RbacUserEntity entity;
            if (CollectionUtils.isNotEmpty(userIds)) {
                if (userIds.size() > 1) {
                    throw new StriveException("只能选择一个用户");
                } else {
                    entity = this.getById(userIds.get(0));
                }
            } else {
                entity = SysAdminUtils.getCurrentUserEntity();
            }
            if (ObjectUtils.isNull(entity)) {
                throw new StriveException("无法获取用户");
            }
            // 判断原始密码是否正确
            if (StringUtils.equals(
                    DigestUtils.md5Hex(
                            dto.getOriginPassword()), entity.getPassword())) {
                entity.setPassword(DigestUtils.md5Hex(dto.getPassword()));
            } else {
                throw new StriveException("原始密码不正确");
            }
            this.updateById(entity);
            this.evictUser(entity.getLoginName());
        }
    }

    @Override
    public void changeTenantPassword(@Valid ChangeUserPasswordDto dto) {
        String md5HexOriginPass = DigestUtils.md5Hex(dto.getOriginPassword());
        RbacUserEntity rbacUserEntity = SysAdminUtils.getCurrentUserEntity();
        if (ObjectUtils.isNull(rbacUserEntity) ||
                !StringUtils.equalsIgnoreCase(md5HexOriginPass,
                        rbacUserEntity.getPassword())) {
            throw new StriveException("原密码不正确");
        }
        if (!StringUtils.equalsIgnoreCase(dto.getPassword(), dto.getConfirmPassword())) {
            throw new StriveException("两次密码输入不一致");
        }
        if (StringUtils.isNotBlank(dto.getLoginName())) {
            rbacUserEntity.setLoginName(dto.getLoginName());
        }
        rbacUserEntity.setPassword(DigestUtils.md5Hex(dto.getPassword()));
        this.baseMapper.updateTenantById(rbacUserEntity);
        this.evictUser(rbacUserEntity.getLoginName());
    }

    /**
     * 更新用户的组织ID
     *
     * @param userId 用户ID
     * @param orgId  组织ID
     */
    private void updateUserOrg(Long userId, Long orgId) {
        RbacUserEntity entity = new RbacUserEntity();
        entity.setId(userId);
        entity.setOrgId(orgId);
        this.updateById(entity);
    }

    /**
     * 重置单个用户的密码
     *
     * @param userId   用户ID
     * @param password 用户密码
     */
    private void changeUserPassword(Long userId, String password) {
        RbacUserEntity entity = this.getById(userId);
        entity.setPassword(DigestUtils.md5Hex(password));
        this.updateById(entity);
        this.evictUser(entity.getLoginName());
    }

    /**
     * 请除用户缓存
     * @param loginName 登录名
     */
    private void evictUser(String loginName) {
        String cacheKey = "loginUser".concat("-").concat(loginName);
        Objects.requireNonNull(cacheManager.getCache("sys")).evict(cacheKey);
    }
}
