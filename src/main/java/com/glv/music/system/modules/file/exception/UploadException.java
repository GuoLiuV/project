package com.glv.music.system.modules.file.exception;

import com.glv.music.system.modules.exception.StriveException;

/**
 * @author ZHOUXIANG
 */
@SuppressWarnings("unused")
public class UploadException extends StriveException {

    public UploadException(){
        super("上传文件保存异常：");
    }

    public UploadException(String message){
        super("上传文件保存异常：" + message);
    }
}
