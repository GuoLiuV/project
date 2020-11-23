package com.glv.project.system.modules.web.utils;

import com.glv.project.system.utils.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZHOUXIANG
 */
public class ReqResContextHolder {

    /**
     * 获取servlet请求
     *
     * @return servlet请求
     */
    public static HttpServletRequest getServletRequest() {
        // 获取响应流
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtils.notNull(servletRequestAttributes)) {
            return servletRequestAttributes.getRequest();
        }
        return null;
    }

    /**
     * 获取servlet响应
     *
     * @return servlet响应
     */
    public static HttpServletResponse getServletResponse() {
        // 获取响应流
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtils.notNull(servletRequestAttributes)) {
            return servletRequestAttributes.getResponse();
        }
        return null;
    }
}
