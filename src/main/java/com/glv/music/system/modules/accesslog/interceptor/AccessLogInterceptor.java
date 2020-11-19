package com.glv.music.system.modules.accesslog.interceptor;

import com.glv.music.system.modules.accesslog.entity.AccessLogEntity;
import com.glv.music.system.modules.accesslog.service.AccessLogService;
import com.glv.music.system.modules.property.StriveProperties;
import com.glv.music.system.modules.sysadmin.SysAdminUtils;
import com.glv.music.system.modules.web.component.SpringContextHolder;
import com.glv.music.system.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZHOUXIANG
 */
@Slf4j
public class AccessLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        String[] ignorePaths = new String[] {
                "/accesslog",
                "/actionlog",
                "/errorlog"
        };
        if (!StringUtils.containsAny(request.getServletPath(), ignorePaths)) {
            try {
                StriveProperties striveProperties =
                        SpringContextHolder.getBean(StriveProperties.class);
                // 启用时保存
                if (striveProperties.isAccessLogEnabled()) {
                    AccessLogEntity logEntity = new AccessLogEntity();
                    logEntity.setUser(SysAdminUtils.getCurrentUserLoginName())
                            .setHost(request.getRemoteHost())
                            .setPath(request.getRequestURI())
                            .setReferrer(request.getHeader("referrer"))
                            .setMethod(request.getMethod())
                            .setUserAgent(request.getHeader("user-agent"))
                            .setProtocol(request.getProtocol());
                    AccessLogService accessLogService = SpringContextHolder.getBean(AccessLogService.class);
                    accessLogService.saveLog(logEntity);
                }
            } catch (Exception e) {
                log.error("保存访问日志时报错", e);
            }
        }
        return true;
    }
}
