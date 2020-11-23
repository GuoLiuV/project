package com.glv.project.system.modules.oauth2.controller;

import com.glv.project.system.modules.oauth2.dto.ClientDetailsDto;
import com.glv.project.system.modules.oauth2.entity.ClientDetailsEntity;
import com.glv.project.system.modules.oauth2.service.StriveClientDetailsService;
import com.glv.project.system.modules.request.annotation.RestPostMapping;
import com.glv.project.system.modules.request.dto.PageRequest;
import com.glv.project.system.modules.response.dto.PageData;
import com.glv.project.system.modules.response.dto.ResponseStatusDto;
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
@RequestMapping("/admin/sys/client")
public class ClientDetailsController {

    @Resource
    private StriveClientDetailsService striveClientDetailsService;

    @ApiOperation("保存客户端信息")
    @RestPostMapping("save")
    public ResponseStatusDto<String> saveClientDetails(@RequestBody ClientDetailsDto dto) {
        striveClientDetailsService.saveClientDetails(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("分页查询客户端信息")
    @RestPostMapping("/list")
    public ResponseStatusDto<PageData<ClientDetailsEntity>> getClientDetailsList(
            @RequestBody PageRequest<ClientDetailsDto> pageRequest) {
        PageData<ClientDetailsEntity> pageData = striveClientDetailsService
                .getClientDetailsList(pageRequest);
        return ResponseStatusDto.success(pageData);
    }

    @ApiOperation("删除客户端信息")
    @RestPostMapping("/delete")
    public ResponseStatusDto<String> deleteClientDetails(@RequestBody ClientDetailsDto dto) {
        striveClientDetailsService.deleteClientDetails(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("批量删除客户端信息")
    @RestPostMapping("/deleteBatch")
    public ResponseStatusDto<String> deleteBatchClientDetails(@RequestBody List<Long> ids) {
        striveClientDetailsService.deleteBatchClientDetails(ids);
        return ResponseStatusDto.success();
    }
}
