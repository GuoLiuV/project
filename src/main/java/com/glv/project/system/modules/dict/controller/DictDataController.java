package com.glv.project.system.modules.dict.controller;

import com.glv.project.system.modules.dict.dto.DictDataDto;
import com.glv.project.system.modules.dict.dto.DictDataRequestDto;
import com.glv.project.system.modules.dict.entity.DictDataEntity;
import com.glv.project.system.modules.dict.service.DictDataService;
import com.glv.project.system.modules.request.annotation.RestGetMapping;
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
@RequestMapping("/admin/sys/dict")
@Api
public class DictDataController {

    @Resource
    private DictDataService dictDataService;

    @ApiOperation("字典项保存")
    @RestPostMapping("/save")
    public ResponseStatusDto<String> saveDict(@RequestBody DictDataDto dictDataDto) {
        dictDataService.saveDict(dictDataDto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("分页查询字典项")
    @RestPostMapping("/list")
    public ResponseStatusDto<PageData<DictDataEntity>> getDictList(@RequestBody PageRequest<DictDataDto> pageRequest){
        PageData<DictDataEntity> pageData = dictDataService.getDictList(pageRequest);
        return ResponseStatusDto.success(pageData);
    }

    @ApiOperation("删除字典项")
    @RestPostMapping("/delete")
    public ResponseStatusDto<String> deleteDict(@RequestBody DictDataDto dictDataDto) {
        dictDataService.deleteDict(dictDataDto);
        return ResponseStatusDto.success();
    }

    @ApiOperation("批量删除字典项")
    @RestPostMapping("/deleteBatch")
    public ResponseStatusDto<String> deleteBatchDict(@RequestBody List<Long> ids) {
        dictDataService.deleteBatchDict(ids);
        return ResponseStatusDto.success();
    }

    @ApiOperation("通过父字典项代码获取字典项")
    @RestGetMapping("/getChildDict")
    public ResponseStatusDto<List<DictDataEntity>> getDictByParentCode(String parentCode) {
        List<DictDataEntity> dictDataEntities = dictDataService.getDictByParentCode(parentCode);
        return ResponseStatusDto.success(dictDataEntities);
    }

    @ApiOperation("通过父字典项代码和值关键字过滤获取字典项")
    @RestPostMapping("/filterChildDict")
    public ResponseStatusDto<List<DictDataEntity>> getDictByParent(@RequestBody DictDataRequestDto dto) {
        List<DictDataEntity> entityList = dictDataService.getDictByParent(dto);
        return ResponseStatusDto.success(entityList);
    }

    @ApiOperation("通过字典代码获取字典值")
    @RestGetMapping("/getDictValueByCode")
    public ResponseStatusDto<String> getDictValueByCode(String code) {
        return ResponseStatusDto.success(
                dictDataService.getDictValueByCode(code)
        );
    }
}
