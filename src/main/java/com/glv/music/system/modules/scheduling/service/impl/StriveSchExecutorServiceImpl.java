package com.glv.music.system.modules.scheduling.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.music.system.modules.exception.StriveException;
import com.glv.music.system.modules.mybatisplus.utils.MyBatisPlusUtils;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import com.glv.music.system.modules.scheduling.dao.StriveSchExecutorMapper;
import com.glv.music.system.modules.scheduling.dto.StriveSchExecutorDto;
import com.glv.music.system.modules.scheduling.entity.StriveSchExecutorEntity;
import com.glv.music.system.modules.scheduling.service.StriveSchExecutorService;
import com.glv.music.system.modules.scheduling.service.StriveSchJobService;
import com.glv.music.system.utils.BeanUtils;
import com.glv.music.system.utils.ObjectUtils;
import com.glv.music.system.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Service
@Slf4j
public class StriveSchExecutorServiceImpl
        extends ServiceImpl<StriveSchExecutorMapper, StriveSchExecutorEntity>
        implements StriveSchExecutorService {

    @Resource
    @Lazy
    private StriveSchJobService striveSchJobService;

    @Override
    public void save(@Valid StriveSchExecutorDto dto) {
        StriveSchExecutorEntity entity = this.getByCodeAndNotId(
                dto.getCode(), dto.getId());
        if (ObjectUtils.notNull(entity)) {
            throw new StriveException("该执行器已存在");
        }
        entity = new StriveSchExecutorEntity();
        BeanUtils.copyPropertiesIgnoreNull(dto, entity);
        this.saveOrUpdate(entity);
    }

    @Override
    public StriveSchExecutorEntity getByCodeAndNotId(String code, Long id) {
        if (StringUtils.isNotBlank(code)) {
            QueryWrapper<StriveSchExecutorEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("code", code);
            if (ObjectUtils.notNull(id)) {
                queryWrapper.ne("id", id);
            }
            return this.getOne(queryWrapper, false);
        }
        return null;
    }

    @Override
    public StriveSchExecutorEntity getExecutorById(Long id) {
        // 忽略租户的查询
        return this.baseMapper.getExecutorById(id);
    }

    @Override
    public List<String> getUrlsById(Long id) {
        String urls = this.baseMapper.getUrlsById(id);
        if (StringUtils.isNotBlank(urls)) {
            urls = StringUtils.removeAllWhitespace(urls);
            String[] urlArray = StringUtils.split(urls, ",");
            if (ObjectUtils.notNull(urlArray) && urlArray.length > 0) {
                return Arrays.asList(urlArray);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public PageData<StriveSchExecutorEntity> list(
            PageRequest<StriveSchExecutorDto> pageRequest) {
        QueryWrapper<StriveSchExecutorEntity> queryWrapper = new QueryWrapper<>();
        MyBatisPlusUtils.buildQuery(queryWrapper, pageRequest, "code");
        IPage<StriveSchExecutorEntity> iPage = pageRequest.buildMybatisPlusPage();
        IPage<StriveSchExecutorEntity> page = this.page(iPage, queryWrapper);
        return new PageData<>(page);
    }

    @Override
    public void deleteById(Long id) {
        if (striveSchJobService.executorHasJob(id)){
            throw new StriveException("该执行器有关联作业不可删除");
        }
        this.removeById(id);
    }
}
