package com.glv.project.system.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ZHOUXIANG
 */
@Slf4j
@SuppressWarnings("unused")
public class TimeUtils {

    /**
     * 延时指定毫秒数
     */
    public static void delay(long millis) {
        try {
            Thread.sleep(millis);
        }catch (Exception e) {
            log.error("延时错误", e);
        }
    }

}
