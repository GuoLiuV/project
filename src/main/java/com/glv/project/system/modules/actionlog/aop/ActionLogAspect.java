package com.glv.project.system.modules.actionlog.aop;

import com.glv.project.system.enums.OperateStatusEnum;
import com.glv.project.system.modules.actionlog.annotation.ActionLog;
import com.glv.project.system.modules.actionlog.entity.ActionLogEntity;
import com.glv.project.system.modules.actionlog.service.ActionLogService;
import com.glv.project.system.modules.exception.StriveException;
import com.glv.project.system.modules.property.StriveProperties;
import com.glv.project.system.modules.sysadmin.SysAdminUtils;
import com.glv.project.system.utils.JSONUtils;
import com.glv.project.system.utils.ObjectUtils;
import com.glv.project.system.utils.RequestUtils;
import com.glv.project.system.utils.SpElUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author ZHOUXIANG
 */
@Slf4j
@Component
@Aspect
public class ActionLogAspect {

    @Resource
    private ActionLogService actionLogService;

    @Resource
    private StriveProperties striveProperties;

    @Around("@annotation(actionLog)")
    public Object handler(ProceedingJoinPoint proceedingJoinPoint,
                          ActionLog actionLog) throws Throwable {
        // 启用行为日志时才会保存
        if (striveProperties.isActionLogEnabled()) {
            //构建Spring表达式参数
            Map<String, Object> variables =
                    ObjectUtils.getAopMethodContext(proceedingJoinPoint);
            // 解析Spring表达式的值
            Object content = SpElUtils.parseExpressionValue(
                    actionLog.content(), variables, Object.class);
            // 构建日志实体
            ActionLogEntity logEntity = new ActionLogEntity();
            logEntity.setTitle(actionLog.title())
                    .setContent(JSONUtils.obj2Json(content))
                    .setDescription(actionLog.description())
                    .setUser(SysAdminUtils.getCurrentUserLoginName())
                    .setMethod(proceedingJoinPoint.getSignature().getName());
            HttpServletRequest request = RequestUtils.getServletRequest();
            if (ObjectUtils.notNull(request)) {
                logEntity.setPath(request.getRequestURI());
            }
            try {
                Object obj = proceedingJoinPoint.proceed();
                logEntity.setStatus(OperateStatusEnum.Y.name());
                actionLogService.saveLog(logEntity);
                return obj;
            } catch (Throwable throwable) {
                logEntity.setStatus(OperateStatusEnum.N.name());
                if (throwable instanceof StriveException) {
                    logEntity.setFail(throwable.getMessage());
                }
                actionLogService.saveLog(logEntity);
                throw throwable;
            }
        } else {
            return proceedingJoinPoint.proceed();
        }
    }

}
