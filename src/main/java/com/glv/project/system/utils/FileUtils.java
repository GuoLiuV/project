package com.glv.project.system.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author ZHOUXIANG
 */
public class FileUtils extends org.apache.commons.io.FileUtils {


    /**
     * 获取文件扩展名
     */
    public static String extension(String fileName) {
        if (fileName == null || !fileName.contains(".")
                || fileName.endsWith(".")) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 路径连接
     */
    public static String joinPath(String parent, String... path) {
        if (StringUtils.isNonEmpty(parent) && path.length > 0) {
            Path filePath = Paths.get(parent, path);
            return filePath.toString();
        }
        return null;
    }

}
