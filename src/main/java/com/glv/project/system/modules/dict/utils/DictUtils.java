package com.glv.project.system.modules.dict.utils;

import com.glv.project.system.modules.dict.entity.DictDataEntity;
import com.glv.project.system.modules.dict.service.DictDataService;
import com.glv.project.system.modules.web.component.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZHOUXIANG
 */
@Slf4j
@SuppressWarnings("unused")
public class DictUtils {

    /**
     * 通过字典键或取字典值
     * @param dictCode 字典代码
     * @return 字典值
     */
    public static String getDictValueByCode(String dictCode) {
        DictDataService dictDataService = SpringContextHolder.getBean(DictDataService.class);
        return dictDataService.getDictValueByCode(dictCode);
    }

    /**
     * 根据字典代码获取字典项
     * @param dictCode 字典代码
     * @return 字典项
     */
    public static DictDataEntity getDictByCode(String dictCode) {
        DictDataService dictDataService = SpringContextHolder.getBean(DictDataService.class);
        return dictDataService.getDictByCode(dictCode);
    }
}
