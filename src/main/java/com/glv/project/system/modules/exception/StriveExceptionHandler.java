package com.glv.project.system.modules.exception;

import com.glv.project.system.modules.errorlog.service.ErrorLogService;
import com.glv.project.system.modules.response.dto.ResponseStatusDto;
import com.glv.project.system.modules.web.component.SpringContextHolder;
import com.glv.project.system.utils.AjaxUtils;
import com.glv.project.system.utils.JSONUtils;
import com.glv.project.system.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * 全局异常拦截器，当发生未经应用程序捕获的异常时，会被此全局异常处理器捕获到，
 * 并向客户端返回异常信息，如果是ajax请求返回json对象，
 * 如果是页面请求，则返回异常错误页面。
 *
 * @author ZHOUXIANG
 */
@ControllerAdvice
@Slf4j
public class StriveExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        try {
            // 首先打印异常原因，无堆栈
            log.error("异常: {}", e.getMessage());
            // 根据ajax和非ajax实现客户端的响应
            if (AjaxUtils.isAjaxRequest(request)) {
                ResponseStatusDto<String> status = ResponseStatusDto.failure();
                // e如果是StriveException或它的子类，则将消息发送到客户端
                // 因为所有StriveException及其子类异常都是开发者抛出的，
                // 消息都是人性化的，因此可以返回到前端提示用户具体如何操作。
                if (e instanceof StriveException) {
                    status = ResponseStatusDto.failure(e.getMessage());
                } else if (e instanceof StriveNoAuthException) {
                    status = ResponseStatusDto.notAuthority("无访问权限");
                } else if (e instanceof InsufficientAuthenticationException){
                    status = ResponseStatusDto.notAuthentication("没有认证");
                } else if (e instanceof ConstraintViolationException) {
                    status = ResponseStatusDto.failure(e.getMessage());
                } else {
                    // 抛出非已知异常，记录异常日志
                    ErrorLogService errorLogService = SpringContextHolder.getBean(ErrorLogService.class);
                    errorLogService.logError(e, request.getServletPath());
                    // 控制台打印异常堆栈
                    log.error("异常堆栈：", e);
                }
                ResponseUtils.writeJson(response, JSONUtils.obj2Json(status));
            } else {
                ModelAndView mv = new ModelAndView("e500");
                mv.addObject("errMsg", e.getMessage());
                return mv;
            }
        } catch (Exception ex) {
            log.error("全局异常捕获后响应处理异常：" + ex.getMessage());
        }
        return null;
    }

}
