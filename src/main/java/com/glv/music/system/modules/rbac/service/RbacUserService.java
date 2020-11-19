package com.glv.music.system.modules.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.music.system.modules.rbac.dto.ChangeUserPasswordDto;
import com.glv.music.system.modules.rbac.dto.RbacUserDto;
import com.glv.music.system.modules.rbac.dto.RbacUserOrgDto;
import com.glv.music.system.modules.rbac.dto.RbacUserRegDto;
import com.glv.music.system.modules.rbac.entity.RbacUserEntity;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface RbacUserService extends IService<RbacUserEntity> {

    /**
     * 租户注册
     *
     * @param regDto 注册信息
     */
    void registerUser(@Valid RbacUserRegDto regDto);

    /**
     * 保存用户
     *
     * @param rbacUserDto 接口模型
     */
    void saveUser(@Valid RbacUserDto rbacUserDto);

    /**
     * 微信手机号直接保存或更新用户并返回登录信息
     *
     * @param mobile 微信绑定的手机号
     * @return 用户信息
     */
    RbacUserEntity getOneByMobileAndInsert(String mobile);

    /**
     * 分页查询用户
     *
     * @param pageRequest 接口模型
     * @return 分页数据
     */
    PageData<RbacUserEntity> getUserList(PageRequest<RbacUserDto> pageRequest);

    /**
     * 根据ID删除用户
     *
     * @param rbacUserDto 接口模型
     */
    void deleteUser(RbacUserDto rbacUserDto);

    /**
     * 根据ID批量删除用户
     *
     * @param ids id集合
     */
    void deleteBatchUser(List<Long> ids);

    /**
     * 通过loginName查询登录用户，非管理员不能登录
     *
     * @param loginName 登录名
     * @return 用户信息
     */
    RbacUserEntity getOneByLoginName(String loginName);

    /**
     * 查询该用户的租户ID
     *
     * @param userId 用户ID
     * @return 租户ID
     */
    Long getTenantIdByUserId(Long userId);

    /**
     * 根据登录名获取租户ID
     *
     * @param loginName 登录名
     * @return 租户ID
     */
    RbacUserEntity getUserByLoginName(String loginName);

    /**
     * 批量修改用户所属组织机构
     *
     * @param dto 接口模型数据
     */
    void changeUsersOrg(RbacUserOrgDto dto);

    /**
     * 修改用户的密码
     *
     * @param dto 修改密码参数
     */
    void changeUsersPassword(@Valid ChangeUserPasswordDto dto);

    /**
     * 租户自己修改密码
     *
     * @param dto 密码信息
     */
    void changeTenantPassword(@Valid ChangeUserPasswordDto dto);

}
