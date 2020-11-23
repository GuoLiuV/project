package com.glv.project.system.utils;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import com.glv.project.system.modules.exception.StriveException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author ZHOUXIANG
 */
@Slf4j
public class CronUtils {

    /**
     * 获取下一次执行时间
     */
    public static Date getNextTime(String cronExpr) {
        Cron cron = validCron(cronExpr);
        ExecutionTime executionTime = ExecutionTime.forCron(cron);
        ZonedDateTime nextTime = executionTime
                .nextExecution(ZonedDateTime.now())
                .orElse(null);
        if (ObjectUtils.isNull(nextTime)) {
            throw new StriveException("获取下一次执行时间失败");
        }
        return Date.from(nextTime.toInstant());
    }

    /**
     * 获取下一次执行时间
     */
    public static Date getNextTime(Cron cron) {
        ExecutionTime executionTime = ExecutionTime.forCron(cron);
        ZonedDateTime nextTime = executionTime
                .nextExecution(ZonedDateTime.now())
                .orElse(null);
        if (ObjectUtils.isNull(nextTime)) {
            throw new StriveException("获取下一次执行时间失败");
        }
        return Date.from(nextTime.toInstant());
    }

    /**
     * 获取上一次执行时间
     */
    public static Date getLastTime(String cronExpr) {
        Cron cron = validCron(cronExpr);
        ExecutionTime executionTime = ExecutionTime.forCron(cron);
        ZonedDateTime lastTime = executionTime
                .lastExecution(ZonedDateTime.now())
                .orElse(null);
        if (ObjectUtils.isNull(lastTime)) {
            throw new StriveException("获取上一次执行时间失败");
        }
        return Date.from(lastTime.toInstant());
    }

    /**
     * 获取上一次执行时间
     */
    public static Date getLastTime(Cron cron) {
        ExecutionTime executionTime = ExecutionTime.forCron(cron);
        ZonedDateTime lastTime = executionTime
                .lastExecution(ZonedDateTime.now())
                .orElse(null);
        if (ObjectUtils.isNull(lastTime)) {
            throw new StriveException("获取上一次执行时间失败");
        }
        return Date.from(lastTime.toInstant());
    }

    /**
     * 验证并返回Cron
     */
    public static Cron validCron(String cronExpr) {
        try {
            CronDefinition cronDefinition = CronDefinitionBuilder
                    .instanceDefinitionFor(CronType.SPRING);
            CronParser cronParser = new CronParser(cronDefinition);
            Cron cron = cronParser.parse(cronExpr);
            cron.validate();
            return cron;
        } catch (Exception e) {
            throw new StriveException("cron表达式验证失败");
        }
    }
}
