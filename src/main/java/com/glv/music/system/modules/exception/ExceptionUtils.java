package com.glv.music.system.modules.exception;

import com.glv.music.system.utils.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author ZHOUXIANG
 */
public class ExceptionUtils {

    /**
     * 获取异常堆栈信息
     *
     * @param e 异常对象
     * @return 堆栈信息
     */
    public static String getPrintStackTrace(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    /**
     * 格式化异常栈将\r\n替换成<br/>
     *
     * @param stackTrace 异常栈跟踪
     * @return html
     */
    public static String formatStackTrace(String stackTrace) {
        if (StringUtils.isNotBlank(stackTrace)) {
            return stackTrace
                    .replaceAll("\r\n", "<br/>");
        }
        return stackTrace;
    }
}
