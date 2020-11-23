package com.glv.project.system.utils;

import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;

/**
 * @author ZHOUXIANG
 */
@Slf4j
public class UrlUtils {

    /**
     * 连接url，首先去掉首尾/符号，再用/符号连接
     */
    public static String joinUrl(
            @NotBlank String url, @NotBlank String path) {
        url = StringUtils.strip(url, "/");
        path = StringUtils.strip(path, "/");
        return url.concat("/").concat(path);
    }
}
