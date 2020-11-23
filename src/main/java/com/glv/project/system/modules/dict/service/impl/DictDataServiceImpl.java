package com.glv.project.system.modules.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.project.system.modules.dict.dao.DictDataMapper;
import com.glv.project.system.modules.dict.dto.DictDataDto;
import com.glv.project.system.modules.dict.dto.DictDataRequestDto;
import com.glv.project.system.modules.dict.entity.DictDataEntity;
import com.glv.project.system.modules.dict.service.DictDataService;
import com.glv.project.system.modules.exception.StriveException;
import com.glv.project.system.modules.mybatisplus.utils.MyBatisPlusUtils;
import com.glv.project.system.modules.request.dto.PageRequest;
import com.glv.project.system.modules.response.dto.PageData;
import com.glv.project.system.utils.BeanUtils;
import com.glv.project.system.utils.CollectionUtils;
import com.glv.project.system.utils.ObjectUtils;
import com.glv.project.system.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Slf4j
@Service
public class DictDataServiceImpl
        extends ServiceImpl<DictDataMapper, DictDataEntity> implements DictDataService {

    @Override
    @CacheEvict(value = "sys", key = "'getDictValueByCode'.concat('_').concat(#dictDataDto.dictCode)")
    public void saveDict(@Valid DictDataDto dictDataDto) {
        // 父级的字典项目的父ID规定为-1
        if (ObjectUtils.isNull(dictDataDto.getParentId())) {
            dictDataDto.setParentId(-1L);
        }
        DictDataEntity dictDataEntity = this.getDictByCodeNotId(
                dictDataDto.getDictCode(), dictDataDto.getId());
        if (ObjectUtils.notNull(dictDataEntity)) {
            throw new StriveException("该字典代码已存在！");
        }
        dictDataEntity = new DictDataEntity();
        BeanUtils.copyPropertiesIgnoreNull(dictDataDto, dictDataEntity);
        // dictCode不同的租户可以重复
        this.saveOrUpdate(dictDataEntity);
    }

    @Override
    public PageData<DictDataEntity> getDictList(PageRequest<DictDataDto> pageRequest) {
        DictDataDto condition = pageRequest.getCondition();
        // 父级的字典项目的父ID规定为-1
        if (ObjectUtils.isNull(condition.getParentId())) {
            condition.setParentId(-1L);
        }
        QueryWrapper<DictDataEntity> queryWrapper = new QueryWrapper<>();
        MyBatisPlusUtils.buildQuery(queryWrapper, condition, "dictCode", "parentId");
        MyBatisPlusUtils.buildSortOrderQuery(queryWrapper, pageRequest.getSort());
        Page<DictDataEntity> page = pageRequest.buildMybatisPlusPage();
        IPage<DictDataEntity> iPage = this.page(page, queryWrapper);
        return new PageData<>(iPage);
    }

    @Override
    public void deleteDict(DictDataDto dictDataDto) {
        if (ObjectUtils.isNull(dictDataDto.getId())) {
            throw new StriveException("要删除的字典项ID为空");
        }
        this.removeById(dictDataDto.getId());
    }

    @Override
    public void deleteBatchDict(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new StriveException("要删除的字典项ID为空");
        }
        this.removeByIds(ids);
    }

    @Override
    @Cacheable(value = "sys", key = "#root.methodName.concat('_').concat(#parentId)")
    public List<DictDataEntity> getDictByParentId(Long parentId) {
        if (ObjectUtils.isNull(parentId)) {
            throw new StriveException("父级字典项ID为空");
        }
        QueryWrapper<DictDataEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PARENT_ID", parentId);
        return this.list(queryWrapper);
    }

    @Override
    @Cacheable(value = "sys", key = "#root.methodName.concat('_').concat(#parentCode)")
    public List<DictDataEntity> getDictByParentCode(String parentCode) {
        if (ObjectUtils.isNull(parentCode)) {
            throw new StriveException("父级字典项代码为空");
        }
        Long parentId = this.getIdByCode(parentCode);
        QueryWrapper<DictDataEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PARENT_ID", parentId);
        queryWrapper.orderByAsc("SEQUENCE");
        return this.list(queryWrapper);
    }

    @Override
    @Cacheable(value = "sys", key = "#root.methodName.concat('_').concat(#code)")
    public String getDictValueByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            QueryWrapper<DictDataEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("DICT_CODE", code);
            try {
                DictDataEntity dictDataEntity = this.getOne(queryWrapper);
                return dictDataEntity.getDictValue();
            } catch (Exception e) {
                log.info("无法获取该字典代码对应的字典值");
            }
        }
        return null;
    }

    @Override
    @Cacheable(value = "sys", key = "#root.methodName.concat('_').concat(#code)")
    public DictDataEntity getDictByCode(String code) {
        QueryWrapper<DictDataEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DICT_CODE", code);
        return this.getOne(queryWrapper);
    }

    @Override
    public DictDataEntity getDictByCodeNotId(String code, Long id) {
        QueryWrapper<DictDataEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DICT_CODE", code)
                .ne(ObjectUtils.notNull(id), "id", id);
        return this.getOne(queryWrapper, false);
    }

    @Override
    public List<DictDataEntity> getDictByParent(DictDataRequestDto dto) {
        Long parentId = this.getIdByCode(dto.getParentCode());
        QueryWrapper<DictDataEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjectUtils.notNull(parentId), "PARENT_ID", parentId)
                .like(StringUtils.isNotBlank(dto.getDictValue()), "DICT_VALUE", dto.getDictValue())
                .eq(StringUtils.isNotBlank(dto.getAddValue()), "ADD_VALUE", dto.getAddValue());
        queryWrapper.orderByAsc("SEQUENCE");
        return this.list(queryWrapper);
    }

    /**
     * 通过字典项目代码获取字典项ID
     *
     * @param code 字典项代码
     * @return 字典项ID
     */
    private Long getIdByCode(String code) {
        DictDataEntity dictDataEntity = this.getDictByCode(code);
        if (ObjectUtils.notNull(dictDataEntity)) {
            return dictDataEntity.getId();
        }
        return null;
    }
}
