package com.glv.music.system.enums;

/**
 * @author ZHOUXIANG
 */
@SuppressWarnings("unused")
public enum PictureEnum {
    // jpg 格式
    JPG("jpg", "image/jpeg"),
    //jpeg 格式
    JPEG("jpeg", "image/jpeg"),
    // png 格式
    PNG("png", "image/png"),
    // gif 格式
    GIF("gif", "image/gif"),
    // tig 格式
    TIF("tif", "image/tiff"),
    // bmp 格式
    BMP("bmp", "image/bmp"),
    // ico 格式
    ICO("ico", "image/x-icon")
    ;

    /**
     * 图片文件扩展名
     */
    public String code;
    /**
     * 图片文件类型
     */
    public String type;

    PictureEnum(String code, String type) {
        this.code = code;
        this.type = type;
    }

}
