package com.glv.music.system.modules.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.music.system.modules.dict.dto.DictDataDto;
import com.glv.music.system.modules.dict.dto.DictDataRequestDto;
import com.glv.music.system.modules.dict.entity.DictDataEntity;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface DictDataService extends IService<DictDataEntity> {

    /**
     * 保存字典项目数据
     *
     * @param dictDataDto 字黄接口数据模型
     */
    void saveDict(@Valid DictDataDto dictDataDto);

    /**
     * 分页查询字典项
     *
     * @param pageRequest 分页查询请求数据
     * @return 分页数据
     */
    PageData<DictDataEntity> getDictList(PageRequest<DictDataDto> pageRequest);

    /**
     * 删除字典项
     *
     * @param dictDataDto 接口模型
     */
    void deleteDict(DictDataDto dictDataDto);

    /**
     * 批量删除字典项
     *
     * @param ids 字典项目id
     */
    void deleteBatchDict(List<Long> ids);

    /**
     * 通过父级字典项ID查询所有子级字典项
     *
     * @param parentId 父级字典项ID
     * @return 所有子级字典项
     */
    List<DictDataEntity> getDictByParentId(Long parentId);

    /**
     * 通过父级字典代码查询所有子级字典项
     *
     * @param parentCode 父级字典代码
     * @return 所有子组字典项
     */
    List<DictDataEntity> getDictByParentCode(String parentCode);

    /**
     * 根据字典代码获取字典值
     *
     * @param code 字典项代码
     * @return 字典项的值
     */
    String getDictValueByCode(String code);

    /**
     * 根据字典代码获取字典对象
     *
     * @param code 字典代码
     * @return 这典对象
     */
    DictDataEntity getDictByCode(String code);

    /**
     * 获取编码不存在的字典
     *
     * @param code 编号
     * @param id   id
     * @return 字典
     */
    DictDataEntity getDictByCodeNotId(String code, Long id);

    /**
     * 根据字典类型代码和值关键字过滤字典项
     *
     * @param dto 请求参数
     * @return 字典项
     */
    List<DictDataEntity> getDictByParent(DictDataRequestDto dto);

}
