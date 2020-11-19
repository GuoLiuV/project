package com.glv.music.system.modules.file.exception;

import com.glv.music.system.modules.exception.StriveException;

/**
 * @author ZHOUXIANG
 */
@SuppressWarnings("unused")
public class DownloadException extends StriveException {

    public DownloadException(){
        super("文件下载异常：");
    }

    public DownloadException(String message){
        super("文件下载异常：" + message);
    }

}
