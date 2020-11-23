package com.glv.project.system.modules.actionlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.project.system.modules.actionlog.dao.ActionLogMapper;
import com.glv.project.system.modules.actionlog.dto.ActionLogQueryDto;
import com.glv.project.system.modules.actionlog.entity.ActionLogEntity;
import com.glv.project.system.modules.actionlog.service.ActionLogService;
import com.glv.project.system.modules.property.StriveProperties;
import com.glv.project.system.modules.request.dto.PageRequest;
import com.glv.project.system.modules.response.dto.PageData;
import com.glv.project.system.utils.ObjectUtils;
import com.glv.project.system.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ZHOUXIANG
 */
@Service
public class ActionLogServiceImpl
        extends ServiceImpl<ActionLogMapper, ActionLogEntity> implements ActionLogService {

    @Resource
    private StriveProperties striveProperties;

    @Override
    public void saveLog(ActionLogEntity actionLogEntity) {
        // 启用行为日志时才会保存
        if (striveProperties.isActionLogEnabled()) {
            this.save(actionLogEntity);
        }
    }

    @Override
    public PageData<ActionLogEntity> getLogList(
            PageRequest<ActionLogQueryDto> pageRequest) {
        ActionLogQueryDto condition = pageRequest.getCondition();
        QueryWrapper<ActionLogEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(condition.getPath()),
                "path", condition.getPath())
                .ge(ObjectUtils.notNull(condition.getStartTime()),
                        "create_time", condition.getStartTime())
                .le(ObjectUtils.notNull(condition.getEndTime()),
                        "create_time", condition.getEndTime())
                .and(StringUtils.isNotBlank(condition.getKeywords()),
                        qw -> qw.or().like("title", condition.getKeywords())
                                .or().like("description", condition.getKeywords())
                                .or().like("method", condition.getKeywords())
                                .or().like("path", condition.getKeywords()));
        queryWrapper.orderByDesc("CREATE_TIME");
        Page<ActionLogEntity> page = pageRequest.buildMybatisPlusPage();
        IPage<ActionLogEntity> iPage = this.page(page, queryWrapper);
        return new PageData<>(iPage);
    }

    @Override
    public void deleteBatch(String unit) {
        QueryWrapper<ActionLogEntity> queryWrapper = new QueryWrapper<>();
        LocalDateTime localDateTime = LocalDateTime.now();
        switch (unit) {
            case "day":
                localDateTime = localDateTime.minusDays(1);
                break;
            case "week":
                localDateTime = localDateTime.minusWeeks(1);
                break;
            case "month":
                localDateTime = localDateTime.minusMonths(1);
                break;
            case "year":
                localDateTime = localDateTime.minusYears(1);
                break;
            default:
                break;
        }
        queryWrapper.le("create_time",
                localDateTime.format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")));
        this.remove(queryWrapper);
    }
}
