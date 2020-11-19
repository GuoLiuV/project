package com.glv.music.system.modules.oauth2.dto;

import com.glv.music.system.modules.rbac.entity.RbacOrgEntity;
import com.glv.music.system.modules.rbac.entity.RbacUserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Data
@Accessors(chain = true)
@ApiModel
public class LoginUserInfoDto {

    @ApiModelProperty("用户基本信息")
    RbacUserEntity user;

    @ApiModelProperty("用户所在组织列表")
    List<RbacOrgEntity> orgs;

    @ApiModelProperty("用户角色")
    HashSet<String> roles;

    @ApiModelProperty("用户权限")
    HashSet<String> auths;

    @ApiModelProperty("用户菜单")
    HashSet<String> menus;

    @ApiModelProperty("用户页面元素")
    HashSet<String> elements;

    @ApiModelProperty("用户页面元素")
    HashSet<String> resources;
}
