package com.glv.music.system.modules.errorlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.music.system.modules.errorlog.dao.ErrorLogMapper;
import com.glv.music.system.modules.errorlog.dto.ErrorLogQueryDto;
import com.glv.music.system.modules.errorlog.entity.ErrorLogEntity;
import com.glv.music.system.modules.errorlog.service.ErrorLogService;
import com.glv.music.system.modules.exception.ExceptionUtils;
import com.glv.music.system.modules.exception.StriveException;
import com.glv.music.system.modules.property.StriveProperties;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import com.glv.music.system.modules.sysadmin.SysAdminUtils;
import com.glv.music.system.utils.ObjectUtils;
import com.glv.music.system.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ZHOUXIANG
 */
@Service
public class ErrorLogServiceImpl
        extends ServiceImpl<ErrorLogMapper, ErrorLogEntity> implements ErrorLogService {

    @Resource
    private StriveProperties striveProperties;

    @Override
    public void logError(Exception e, String path) {
        // 启用错误日志记录时才会保存
        if (striveProperties.isErrorLogEnabled()) {
            ErrorLogEntity errorLogEntity = new ErrorLogEntity();
            // 获取并格式化异常堆栈信息
            String stackInfo = ExceptionUtils.getPrintStackTrace(e);
            stackInfo = ExceptionUtils.formatStackTrace(stackInfo);
            // 设置异常日志数据
            String message = "底层抛出异常";
            if (e instanceof StriveException) {
                message = e.getMessage();
            }
            errorLogEntity.setUser(SysAdminUtils.getCurrentUserLoginName())
                    .setMessage(message)
                    .setStackInfo(stackInfo)
                    .setPath(path);
            this.save(errorLogEntity);
        }
    }

    @Override
    public PageData<ErrorLogEntity> getLogList(
            PageRequest<ErrorLogQueryDto> pageRequest) {
        ErrorLogQueryDto condition = pageRequest.getCondition();
        QueryWrapper<ErrorLogEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(condition.getPath()),
                "path", condition.getPath())
                .ge(ObjectUtils.notNull(condition.getStartTime()),
                        "create_time", condition.getStartTime())
                .le(ObjectUtils.notNull(condition.getEndTime()),
                        "create_time", condition.getEndTime())
                .and(StringUtils.isNotBlank(condition.getKeywords()),
                        qw -> qw.or().like("message", condition.getKeywords())
                                .or().like("path", condition.getKeywords()));
        queryWrapper.orderByDesc("CREATE_TIME");
        Page<ErrorLogEntity> page = pageRequest.buildMybatisPlusPage();
        IPage<ErrorLogEntity> iPage = this.page(page, queryWrapper);
        return new PageData<>(iPage);
    }

    @Override
    public void deleteBatch(String unit) {
        QueryWrapper<ErrorLogEntity> queryWrapper = new QueryWrapper<>();
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
