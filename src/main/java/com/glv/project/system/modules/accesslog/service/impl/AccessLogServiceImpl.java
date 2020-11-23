package com.glv.project.system.modules.accesslog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.project.system.modules.accesslog.dao.AccessLogMapper;
import com.glv.project.system.modules.accesslog.dto.AccessLogQueryDto;
import com.glv.project.system.modules.accesslog.entity.AccessLogEntity;
import com.glv.project.system.modules.accesslog.service.AccessLogService;
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
public class AccessLogServiceImpl
        extends ServiceImpl<AccessLogMapper, AccessLogEntity> implements AccessLogService {
    @Resource
    private StriveProperties striveProperties;

    @Override
    public void saveLog(AccessLogEntity accessLogEntity) {
        // 启用访问日志时才会保存
        if (striveProperties.isAccessLogEnabled()) {
            this.save(accessLogEntity);
        }
    }

    @Override
    public PageData<AccessLogEntity> getLogList(
            PageRequest<AccessLogQueryDto> pageRequest) {
        AccessLogQueryDto condition = pageRequest.getCondition();
        QueryWrapper<AccessLogEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(condition.getPath()),
                "path", condition.getPath())
                .ge(ObjectUtils.notNull(condition.getStartTime()),
                        "create_time", condition.getStartTime())
                .le(ObjectUtils.notNull(condition.getEndTime()),
                        "create_time", condition.getEndTime())
                .and(StringUtils.isNotBlank(condition.getKeywords()),
                        qw -> qw.or().like("method", condition.getKeywords())
                                .or().like("path", condition.getKeywords())
                                .or().like("host", condition.getKeywords()));
        queryWrapper.orderByDesc("CREATE_TIME");
        Page<AccessLogEntity> page = pageRequest.buildMybatisPlusPage();
        IPage<AccessLogEntity> iPage = this.page(page, queryWrapper);
        return new PageData<>(iPage);
    }

    @Override
    public void deleteBatch(String unit) {
        QueryWrapper<AccessLogEntity> queryWrapper = new QueryWrapper<>();
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
