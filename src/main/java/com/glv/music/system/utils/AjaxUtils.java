package com.glv.music.system.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ZHOUXIANG
 */
public class AjaxUtils {

    /**
     * <p>判断是否为数据请求，否则是页面请求</p>
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        String contentType = request.getContentType();
        return StringUtils.equalsIgnoreCase("XMLHttpRequest", requestType)
                || StringUtils.containsIgnoreCase(contentType, "application/json")
                || StringUtils.containsIgnoreCase(contentType, "application/x-www-form-urlencoded");
    }

}
