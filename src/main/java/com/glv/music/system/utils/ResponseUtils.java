package com.glv.music.system.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.zip.ZipOutputStream;

/**
 * @author ZHOUXIANG
 */
@Slf4j
public class ResponseUtils {

    /**
     * 向Servlet Response 响应输出 application/json
     */
    public static void writeJson(HttpServletResponse response, String content) {
        response.setContentType("application/json;charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            IOUtils.write(content, out);
            out.flush();
        } catch (Exception e) {
            log.error("向ServletResponse输出异常", e);
        }
    }

    /**
     * 向Servlet Response 响应输出 text/html
     */
    public static void writeHtml(HttpServletResponse response, String content) {
        response.setContentType("text/html;charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            IOUtils.write(content, out);
            out.flush();
        } catch (Exception e) {
            log.error("向ServletResponse输出异常", e);
        }
    }

    /**
     * 获取http servlet response 响应对应
     */
    public static HttpServletResponse getResponse() {
        // 获取响应流
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        return servletRequestAttributes.getResponse();
    }

    /**
     * 向响应输入流数据
     */
    public static void writeStream(HttpServletResponse response, String filename, InputStream inputStream) {
        try {
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes()));
            response.setContentType("application/octet-stream");
            response.resetBuffer();
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            log.error("输出响应流失败:{}", e.getMessage());
        }
    }

    /**
     * 向响应输入压缩流数据
     */
    public static void writeZipStream(HttpServletResponse response,
                                      InputStream inputStream, String entryname, String filename) {
        try {
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes()));
            response.setContentType("application/zip");
            response.resetBuffer();
            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
            ZipUtils.putNextEntry(zipOutputStream, inputStream, entryname);
            zipOutputStream.close();
            response.flushBuffer();
        } catch (Exception e) {
            log.error("输出响应流失败:{}", e.getMessage());
        }
    }
}
