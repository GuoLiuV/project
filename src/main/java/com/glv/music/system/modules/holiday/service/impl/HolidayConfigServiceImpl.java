package com.glv.music.system.modules.holiday.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.music.system.enums.TimeUnitEnum;
import com.glv.music.system.modules.exception.StriveException;
import com.glv.music.system.modules.holiday.dao.HolidayConfigMapper;
import com.glv.music.system.modules.holiday.dto.HolidayConfigDto;
import com.glv.music.system.modules.holiday.entity.HolidayConfigEntity;
import com.glv.music.system.modules.holiday.service.HolidayConfigService;
import com.glv.music.system.modules.mybatisplus.utils.MyBatisPlusUtils;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import com.glv.music.system.modules.sysadmin.SysAdminUtils;
import com.glv.music.system.utils.BeanUtils;
import com.glv.music.system.utils.CollectionUtils;
import com.glv.music.system.utils.DateUtils;
import com.glv.music.system.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ZHOUXIANG
 */
@Service
@Slf4j
public class HolidayConfigServiceImpl
        extends ServiceImpl<HolidayConfigMapper, HolidayConfigEntity> implements HolidayConfigService {

    @Override
    public void saveHolidayConfig(HolidayConfigDto dto) {
        // 提取年份数据
        Map<TimeUnitEnum, Integer> dateInfo = DateUtils.getInfoFromDateString(dto.getDate());
        dto.setYear(dateInfo.get(TimeUnitEnum.YEAR));
        HolidayConfigEntity entity = new HolidayConfigEntity();
        BeanUtils.copyPropertiesIgnoreNull(dto, entity);
        Long tenantId = SysAdminUtils.getCurrentTenantId();
        entity.setTenantId(tenantId);
        try {
            if (!this.saveOrUpdate(entity)) {
                throw new StriveException("保存节假日配置失败");
            }
        } catch (DuplicateKeyException e) {
            throw new StriveException("该节假日配置日期已存在");
        }
    }

    @Override
    public PageData<HolidayConfigEntity> getHolidayConfigList(PageRequest<HolidayConfigDto> pageRequest) {
        HolidayConfigDto dto = pageRequest.getCondition();
        if (ObjectUtils.isNull(dto.getYear())) {
            dto.setYear(DateUtils.getCurrentYear());
        }
        QueryWrapper<HolidayConfigEntity> queryWrapper = new QueryWrapper<>();
        MyBatisPlusUtils.buildQuery(queryWrapper, dto, "date", "type");
        MyBatisPlusUtils.buildSortOrderQuery(queryWrapper, pageRequest.getSort());
        Page<HolidayConfigEntity> page = pageRequest.buildMybatisPlusPage();
        IPage<HolidayConfigEntity> iPage = this.page(page, queryWrapper);
        return new PageData<>(iPage);
    }

    @Override
    public void deleteHolidayConfig(HolidayConfigDto dto) {
        if (ObjectUtils.isNull(dto.getId())) {
            throw new StriveException("节假日配置ID为空");
        }
        this.removeById(dto.getId());
    }

    @Override
    public void deleteBatchHolidayConfig(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new StriveException("要删除的节假日配置ID为空");
        }
        this.removeByIds(ids);
    }
}
