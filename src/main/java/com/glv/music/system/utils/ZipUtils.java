package com.glv.music.system.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author ZHOUXIANG
 */
@Slf4j
public class ZipUtils {

    /**
     * 向压缩流中添加一个文件
     */
    public static void putNextEntry(ZipOutputStream zipOutputStream,
                                    InputStream inputStream,
                                    String entryName) throws IOException {
        zipOutputStream.putNextEntry(new ZipEntry(entryName));
        IOUtils.copy(inputStream, zipOutputStream);
    }
}
