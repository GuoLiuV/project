package com.glv.music.system.modules.holiday.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.music.system.modules.holiday.dto.HolidayConfigDto;
import com.glv.music.system.modules.holiday.entity.HolidayConfigEntity;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface HolidayConfigService extends IService<HolidayConfigEntity> {

    /**
     * 节假日配置保存
     * @param dto 节假日配置数据模型
     */
    void saveHolidayConfig(@Valid HolidayConfigDto dto);

    /**
     * 分页查询节假日配置
     * @param pageRequest 请求接口
     * @return 分页查询结果
     */
    PageData<HolidayConfigEntity> getHolidayConfigList(PageRequest<HolidayConfigDto> pageRequest);

    /**
     * 删除节假日配置
     * @param dto 数据接口
     */
    void deleteHolidayConfig(HolidayConfigDto dto);

    /**
     * 批量删除节假日配置
     * @param ids ID LIST
     */
    void deleteBatchHolidayConfig(List<Long> ids);
}
