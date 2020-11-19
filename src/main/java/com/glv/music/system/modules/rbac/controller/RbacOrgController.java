package com.glv.music.system.modules.rbac.controller;

import com.glv.music.system.modules.rbac.dto.OrgTreeDto;
import com.glv.music.system.modules.rbac.dto.RbacOrgDto;
import com.glv.music.system.modules.rbac.entity.RbacOrgEntity;
import com.glv.music.system.modules.rbac.service.RbacOrgService;
import com.glv.music.system.modules.request.annotation.RestGetMapping;
import com.glv.music.system.modules.request.annotation.RestPostMapping;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import com.glv.music.system.modules.response.dto.ResponseStatusDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Controller
@Api
@RequestMapping("/admin/sys/org")
public class RbacOrgController {

    @Resource
    private RbacOrgService rbacOrgService;

    @ApiOperation("保存组织")
    @RestPostMapping("/save")
    public ResponseStatusDto<String> saveOrg(@RequestBody RbacOrgDto rbacOrgDto) {
        rbacOrgService.saveOrg(rbacOrgDto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("更新顶层组织")
    @RestPostMapping("/changeTopOrg")
    public ResponseStatusDto<String> changeTop(@RequestBody RbacOrgDto rbacOrgDto) {
        rbacOrgService.changeTopOrgInfo(rbacOrgDto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("获取顶层组织")
    @RestGetMapping("/getCompany")
    public ResponseStatusDto<RbacOrgEntity> getCompany() {
        return ResponseStatusDto.success(
                rbacOrgService.getTopOrgByTenantId()
        );
    }

    @ApiOperation("分页查询组织")
    @RestPostMapping("/list")
    public ResponseStatusDto<PageData<RbacOrgEntity>> getOrgList(
            @RequestBody PageRequest<RbacOrgDto> pageRequest) {
        PageData<RbacOrgEntity> pageData = rbacOrgService.getOrgList(pageRequest);
        return ResponseStatusDto.success(pageData);
    }

    @ApiOperation("删除组织")
    @RestPostMapping("/delete")
    public ResponseStatusDto<String> deleteOrg(@RequestBody RbacOrgDto rbacOrgDto) {
        rbacOrgService.deleteOrg(rbacOrgDto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("批量删除组织")
    @RestPostMapping("/deleteBatch")
    public ResponseStatusDto<String> deleteBatchOrg(@RequestBody List<Long> ids) {
        rbacOrgService.deleteBatchOrg(ids);
        return ResponseStatusDto.success();
    }

    @ApiOperation("获取组织树数据")
    @RestGetMapping("/tree")
    public ResponseStatusDto<List<OrgTreeDto>> getOrgTree() {
        List<OrgTreeDto> nodes = rbacOrgService.getOrgTree();
        return ResponseStatusDto.success(nodes);
    }

    @ApiOperation("获取父组织下的子组织")
    @RestGetMapping("/listByParentId")
    public ResponseStatusDto<List<RbacOrgEntity>> getOrgListByParentId(Long parentId) {
        List<RbacOrgEntity> children = rbacOrgService.getOrgListByParentId(parentId);
        return ResponseStatusDto.success(children);
    }

    @ApiOperation("获取用户所在的组织树")
    @RestGetMapping("/getUserLevelOrgList")
    public ResponseStatusDto<List<RbacOrgEntity>> getUserLevelOrgList(Long userId) {
        return ResponseStatusDto.success(
                rbacOrgService.getUserLevelOrgList(userId)
        );
    }

    @ApiOperation("获取用户所在的组织")
    @RestGetMapping("/getUserOrg")
    public ResponseStatusDto<RbacOrgEntity> getUserOrg(Long userId) {
        return ResponseStatusDto.success(
                rbacOrgService.getUserOrg(userId)
        );
    }
}
