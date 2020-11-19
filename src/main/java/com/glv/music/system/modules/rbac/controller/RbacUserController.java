package com.glv.music.system.modules.rbac.controller;


import com.glv.music.system.modules.file.service.FileService;
import com.glv.music.system.modules.rbac.dto.ChangeUserPasswordDto;
import com.glv.music.system.modules.rbac.dto.RbacUserDto;
import com.glv.music.system.modules.rbac.dto.RbacUserOrgDto;
import com.glv.music.system.modules.rbac.entity.RbacUserEntity;
import com.glv.music.system.modules.rbac.service.RbacUserService;
import com.glv.music.system.modules.request.annotation.RestGetMapping;
import com.glv.music.system.modules.request.annotation.RestPostMapping;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import com.glv.music.system.modules.response.dto.ResponseStatusDto;
import com.glv.music.system.modules.sysadmin.SysAdminUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Controller
@Api
@RequestMapping("/admin/sys/user")
public class RbacUserController {

    /**
     * 用户图像的相对路径
     */
    private static final String AVATAR_PATH = "/avatar";

    @Resource
    private RbacUserService rbacUserService;

    @Resource
    private FileService fileService;

    @ApiOperation("保存用户")
    @RestPostMapping("/save")
    public ResponseStatusDto<String> saveUser(@RequestBody RbacUserDto rbacUserDto) {
        rbacUserService.saveUser(rbacUserDto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("根据ID获取用户")
    @RestGetMapping("/getById")
    public ResponseStatusDto<RbacUserEntity> getUserById(Long userId) {
        RbacUserEntity userEntity = rbacUserService.getById(userId);
        return ResponseStatusDto.success(userEntity);
    }

    @ApiOperation("分页查询用户")
    @RestPostMapping("/list")
    public ResponseStatusDto<PageData<RbacUserEntity>> getUserList(
            @RequestBody PageRequest<RbacUserDto> pageRequest) {
        PageData<RbacUserEntity> pageData = rbacUserService.getUserList(pageRequest);
        return ResponseStatusDto.success(pageData);
    }

    @ApiOperation("删除用户")
    @RestPostMapping("/delete")
    public ResponseStatusDto<String> deleteUser(
            @RequestBody RbacUserDto rbacUserDto) {
        rbacUserService.deleteUser(rbacUserDto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("批量删除用户")
    @RestPostMapping("/deleteBatch")
    public ResponseStatusDto<String> deleteUsers(@RequestBody List<Long> ids) {
        rbacUserService.deleteBatchUser(ids);
        return ResponseStatusDto.success();
    }

    @ApiOperation("上传用户图像")
    @RestPostMapping("/uploadAvatar")
    public ResponseStatusDto<String> uploadAvatar(MultipartFile file) {
        String filePath = fileService.saveUploadFile(file, AVATAR_PATH);
        return ResponseStatusDto.success(filePath);
    }

    @ApiOperation("批量更改用户所在组织")
    @RestPostMapping("/changeUserOrg")
    public ResponseStatusDto<String> changeUsersOrg(
            @RequestBody RbacUserOrgDto dto) {
        rbacUserService.changeUsersOrg(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("修改用户的密码")
    @RestPostMapping("/changeUserPassword")
    public ResponseStatusDto<String> changeUserPassword(
            @RequestBody ChangeUserPasswordDto dto) {
        rbacUserService.changeUsersPassword(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("修改用户的密码")
    @RestPostMapping("/changeTenantPassword")
    public ResponseStatusDto<String> changeTenantPassword(
            @RequestBody ChangeUserPasswordDto dto) {
        rbacUserService.changeTenantPassword(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("获取当前登录用户")
    @RestGetMapping("/getCurrentUserEntity")
    public ResponseStatusDto<RbacUserEntity> getCurrentUserEntity() {
        return ResponseStatusDto.success(
                SysAdminUtils.getCurrentUserEntity()
        );
    }

    @ApiOperation("获取当前用户登录名")
    @RestGetMapping("/getCurrentLoginName")
    public ResponseStatusDto<String> getCurrentLoginName() {
        return ResponseStatusDto.success(
                SysAdminUtils.getCurrentUserLoginName()
        );
    }

    @ApiOperation("获取当前租户ID")
    @RestGetMapping("/getCurrentTenantId")
    public ResponseStatusDto<Long> getCurrentTenantId() {
        return ResponseStatusDto.success(
                SysAdminUtils.getCurrentTenantId()
        );
    }

    @ApiOperation("根据用户ID获取当前租户ID")
    @RestGetMapping("/getTenantIdByUserId")
    public ResponseStatusDto<Long> getTenantIdByUserId(Long userId) {
        return ResponseStatusDto.success(
                rbacUserService.getTenantIdByUserId(userId)
        );
    }
}
