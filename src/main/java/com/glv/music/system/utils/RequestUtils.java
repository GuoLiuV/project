package com.glv.music.system.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取Request对象属性值工具类
 *
 * @author ZHOUXIANG
 */
public class RequestUtils {

    /**
     * 获取请求上下文路径
     */
    public static String getContextPath() {
        String contextPath = "";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtils.notNull(attributes)) {
            HttpServletRequest request = attributes.getRequest();
            contextPath = request.getContextPath();
        }
        return contextPath;
    }

    /**
     * 获取请求中的参数值
     */
    public static String getRequestParameter(String name) {
        String value = null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtils.notNull(attributes)) {
            HttpServletRequest request = attributes.getRequest();
            value = request.getParameter(name);
        }
        return value;
    }

    /**
     * 获取Servlet请求对象
     */
    public static HttpServletRequest getServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtils.notNull(attributes)) {
            return attributes.getRequest();
        }
        return null;
    }

    /**
     * 获取请求中的属性值
     */
    public static Object getRequestAttribute(String name) {
        Object value = null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtils.notNull(attributes)) {
            HttpServletRequest request = attributes.getRequest();
            value = request.getAttribute(name);
        }
        return value;
    }

}
