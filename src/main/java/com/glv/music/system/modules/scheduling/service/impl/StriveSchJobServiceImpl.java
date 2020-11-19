package com.glv.music.system.modules.scheduling.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.music.system.modules.exception.StriveException;
import com.glv.music.system.modules.mybatisplus.utils.MyBatisPlusUtils;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import com.glv.music.system.modules.scheduling.dao.StriveSchJobMapper;
import com.glv.music.system.modules.scheduling.disruptor.SchDisruptorConfig;
import com.glv.music.system.modules.scheduling.dto.StriveSchJobDto;
import com.glv.music.system.modules.scheduling.entity.StriveSchJobEntity;
import com.glv.music.system.modules.scheduling.enums.JobStatusEnum;
import com.glv.music.system.modules.scheduling.service.StriveSchExecutorService;
import com.glv.music.system.modules.scheduling.service.StriveSchJobService;
import com.glv.music.system.modules.scheduling.service.StriveSchedulerService;
import com.glv.music.system.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Service
@Slf4j
public class StriveSchJobServiceImpl
        extends ServiceImpl<StriveSchJobMapper, StriveSchJobEntity>
        implements StriveSchJobService {

    @Resource
    @Lazy
    private StriveSchedulerService striveSchedulerService;

    @Resource
    @Lazy
    private StriveSchExecutorService striveSchExecutorService;

    @Resource
    private SchDisruptorConfig schDisruptorConfig;

    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(@Valid StriveSchJobDto dto) {
        StriveSchJobEntity entity =
                this.getByCodeAndNotId(dto.getCode(), dto.getId());
        if (ObjectUtils.notNull(entity)) {
            throw new StriveException("该作业任务已经存在");
        }
        // 验证输入的cron表达式是否正确
        CronUtils.validCron(dto.getCron());
        // 创建实体
        entity = new StriveSchJobEntity();
        BeanUtils.copyPropertiesIgnoreNull(dto, entity);
        if (StringUtils.isBlank(entity.getStatus())) {
            entity.setStatus(JobStatusEnum.STOP.name());
        }
        // 更新下一次执行时间
        this.updateNextTime(entity);
        // 保存或更新
        this.saveOrUpdate(entity);
    }

    @Override
    public StriveSchJobEntity getByCodeAndNotId(String code, Long id) {
        if (StringUtils.isNotBlank(code)) {
            QueryWrapper<StriveSchJobEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("code", code);
            if (ObjectUtils.notNull(id)) {
                queryWrapper.ne("id", id);
            }
            return this.getOne(queryWrapper, false);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJob(Long id) {
        // 删除数据库中的作业
        this.removeById(id);
    }

    @Override
    public PageData<StriveSchJobEntity> list(
            PageRequest<StriveSchJobDto> pageRequest) {
        QueryWrapper<StriveSchJobEntity> queryWrapper = new QueryWrapper<>();
        MyBatisPlusUtils.buildQuery(queryWrapper, pageRequest, "code");
        IPage<StriveSchJobEntity> iPage = pageRequest.buildMybatisPlusPage();
        IPage<StriveSchJobEntity> page = this.page(iPage, queryWrapper);
        return new PageData<>(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startJob(Long id) {
        if (ObjectUtils.notNull(id)) {
            StriveSchJobEntity entity = this.getById(id);
            entity.setStatus(JobStatusEnum.RUNNING.name());
            this.updateById(entity);
            // 更新下一次执行时间
            this.updateNextTime(entity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void stopJob(Long id) {
        if (ObjectUtils.notNull(id)) {
            StriveSchJobEntity entity = new StriveSchJobEntity();
            entity.setId(id);
            entity.setStatus(JobStatusEnum.STOP.name());
            this.updateById(entity);
        }
    }

    @Override
    public void runJobOnce(Long id) {
        StriveSchJobEntity entity = this.getById(id);
        schDisruptorConfig.publishEvent(entity);
    }

    @Override
    public void listRunningJob(ResultHandler<StriveSchJobEntity> handler) {
        QueryWrapper<StriveSchJobEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        // 忽略租户的查询
        this.baseMapper.listRunningJob(queryWrapper, handler);
    }

    @Override
    public void initJobNextTime() {
        try {
            this.listRunningJob(resultContext -> {
                StriveSchJobEntity entity = resultContext.getResultObject();
                this.updateNextTime(entity);
            });
        } catch (Exception e) {
            log.error("初始化任务下一次执行时间异常：{}", e.getMessage());
        }
    }

    @Override
    public boolean executorHasJob(Long execId) {
        QueryWrapper<StriveSchJobEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exec_id", execId);
        StriveSchJobEntity entity = this.getOne(queryWrapper, false);
        return ObjectUtils.notNull(entity);
    }

    @Override
    public String getEndPointUrl(StriveSchJobEntity entity) {
        String url = this.rotateUrlByExecutorId(entity.getExecId());
        if (ObjectUtils.isNull(url)) {
            throw new StriveException("无法获取执行器服务地址");
        }
        return UrlUtils.joinUrl(url, entity.getEndpoint());
    }

    @Override
    public void updateNextTime(StriveSchJobEntity entity) {
        // 获取下一次执行时间
        Date nextTime = CronUtils.getNextTime(entity.getCron());
        // 更新数据库下一次执行时间
        this.baseMapper.updateNextTime(entity.getId(),
                DateUtils.formatDatetime(nextTime));
    }

    /**
     * 轮询获取执行服务地址
     *
     * @param execId 执行器ID
     * @return 执行器地址
     */
    private String rotateUrlByExecutorId(Long execId) {
        if (ObjectUtils.notNull(execId)) {
            List<String> urlList = striveSchExecutorService.getUrlsById(execId);
            if (CollectionUtils.isNotEmpty(urlList)) {
                String key = "executor_url:".concat(String.valueOf(execId));
                Long cursor = redisTemplate.opsForValue()
                        .increment(key, 1);
                if (ObjectUtils.notNull(cursor)) {
                    long size = urlList.size();
                    return urlList.get((int) (cursor % size));
                }
            }
        }
        return null;
    }
}
