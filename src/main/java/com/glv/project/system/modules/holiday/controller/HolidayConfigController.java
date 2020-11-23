package com.glv.project.system.modules.holiday.controller;

import com.glv.project.system.modules.holiday.dto.HolidayConfigDto;
import com.glv.project.system.modules.holiday.entity.HolidayConfigEntity;
import com.glv.project.system.modules.holiday.service.HolidayConfigService;
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
@RequestMapping("/admin/sys/holiday")
@Api
public class HolidayConfigController {

    @Resource
    private HolidayConfigService holidayConfigService;

    @ApiOperation("保存节假日配置")
    @RestPostMapping("/save")
    public ResponseStatusDto<String> saveHolidayConfig(@RequestBody HolidayConfigDto dto) {
        holidayConfigService.saveHolidayConfig(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("分页查询节假日配置")
    @RestPostMapping("/list")
    public ResponseStatusDto<PageData<HolidayConfigEntity>> getHolidayConfigList(
            @RequestBody PageRequest<HolidayConfigDto> pageRequest) {
        PageData<HolidayConfigEntity> pageData = holidayConfigService
                .getHolidayConfigList(pageRequest);
        return ResponseStatusDto.success(pageData);
    }

    @ApiOperation("删除节假日配置")
    @RestPostMapping("/delete")
    public ResponseStatusDto<String> deleteHolidayConfig(@RequestBody HolidayConfigDto dto) {
        holidayConfigService.deleteHolidayConfig(dto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("批量删除节假日配置")
    @RestPostMapping("/deleteBatch")
    public ResponseStatusDto<String> deleteBatchHolidayConfig(@RequestBody List<Long> ids) {
        holidayConfigService.deleteBatchHolidayConfig(ids);
        return ResponseStatusDto.success();
    }

}
