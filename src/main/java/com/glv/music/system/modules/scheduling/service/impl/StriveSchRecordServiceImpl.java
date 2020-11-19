package com.glv.music.system.modules.scheduling.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.music.system.modules.exception.ExceptionUtils;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import com.glv.music.system.modules.scheduling.dao.StriveSchRecordMapper;
import com.glv.music.system.modules.scheduling.dto.StriveSchRecordDto;
import com.glv.music.system.modules.scheduling.entity.StriveSchRecordEntity;
import com.glv.music.system.modules.scheduling.enums.JobStatusEnum;
import com.glv.music.system.modules.scheduling.service.StriveSchRecordService;
import com.glv.music.system.utils.BeanUtils;
import com.glv.music.system.utils.DateUtils;
import com.glv.music.system.utils.ObjectUtils;
import com.glv.music.system.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author ZHOUXIANG
 */
@Service
@Slf4j
public class StriveSchRecordServiceImpl
        extends ServiceImpl<StriveSchRecordMapper, StriveSchRecordEntity>
        implements StriveSchRecordService {

    @Override
    public void save(@Valid StriveSchRecordDto dto) {
        StriveSchRecordEntity entity = new StriveSchRecordEntity();
        BeanUtils.copyPropertiesIgnoreNull(dto, entity);
        entity.setStartTime(new Date());
        entity.setStatus(JobStatusEnum.RUNNING.name());
        this.saveOrUpdate(entity);
        dto.setId(entity.getId());
    }

    @Override
    public PageData<StriveSchRecordEntity> list(
            PageRequest<StriveSchRecordDto> pageRequest) {
        StriveSchRecordDto condition = pageRequest.getCondition();
        QueryWrapper<StriveSchRecordEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjectUtils.notNull(condition.getId()),
                "id", condition.getId())
                .eq(StringUtils.isNotBlank(condition.getStatus()),
                        "status", condition.getStatus())
                .eq(ObjectUtils.notNull(condition.getJobId()),
                        "job_id", condition.getJobId());
        queryWrapper.like(StringUtils.isNotBlank(condition.getJobName()),
                "job_name", condition.getJobName())
                .ge(ObjectUtils.notNull(condition.getStartTime()),
                        "create_time",
                        DateUtils.formatDatetime(condition.getStartTime()))
                .le(ObjectUtils.notNull(condition.getEndTime()),
                        "create_time",
                        DateUtils.formatDatetime(condition.getEndTime()));
        queryWrapper.orderByDesc("create_time");
        IPage<StriveSchRecordEntity> iPage = pageRequest.buildMybatisPlusPage();
        IPage<StriveSchRecordEntity> page = this.page(iPage, queryWrapper);
        return new PageData<>(page);
    }

    @Override
    public void updateStackTrace(Long id, Exception e) {
        String stackInfo = ExceptionUtils.getPrintStackTrace(e);
        stackInfo = ExceptionUtils.formatStackTrace(stackInfo);
        StriveSchRecordEntity entity = new StriveSchRecordEntity();
        entity.setId(id);
        entity.setStatus(JobStatusEnum.ERROR.name())
                .setEndTime(new Date())
                .setStackTrace(stackInfo);
        this.baseMapper.updateStackTrace(entity);
    }

    @Override
    public void updateStatus(Long id, String status) {
        StriveSchRecordEntity entity = new StriveSchRecordEntity();
        entity.setId(id);
        entity.setStatus(status).setEndTime(new Date());
        this.baseMapper.updateStatus(entity);
    }

    @Override
    public void deleteBatch(String unit) {
        switch (unit) {
            case "day":
                this.deleteDayAgo();
                break;
            case "week":
                this.deleteWeekAgo();
                break;
            case "month":
                this.deleteMonthAgo();
                break;
            case "year":
                this.deleteYearAgo();
                break;
            case "all":
                this.deleteAll();
                break;
            default:
                break;
        }
    }

    /**
     * 删除所有日志
     */
    private void deleteAll() {
        QueryWrapper<StriveSchRecordEntity> queryWrapper = new QueryWrapper<>();
        this.remove(queryWrapper);
    }

    /**
     * 删除一日之前的数据
     */
    private void deleteDayAgo() {
        String dayAgo = LocalDateTime.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        QueryWrapper<StriveSchRecordEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("create_time", dayAgo);
        this.remove(queryWrapper);
    }

    /**
     * 删除一周之前的数据
     */
    private void deleteWeekAgo() {
        String dayAgo = LocalDateTime.now().minusWeeks(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        QueryWrapper<StriveSchRecordEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("create_time", dayAgo);
        this.remove(queryWrapper);
    }

    /**
     * 删除一年之前的数据
     */
    private void deleteMonthAgo() {
        String dayAgo = LocalDateTime.now().minusMonths(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        QueryWrapper<StriveSchRecordEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("create_time", dayAgo);
        this.remove(queryWrapper);
    }

    /**
     * 删除一年之前的数据
     */
    private void deleteYearAgo() {
        String dayAgo = LocalDateTime.now().minusYears(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        QueryWrapper<StriveSchRecordEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("create_time", dayAgo);
        this.remove(queryWrapper);
    }
}
