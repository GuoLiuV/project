package com.glv.music.system.modules.qrcode.service;

import java.io.File;

/**
 * @author ZHOUXIANG
 */
public interface QrCodeService {

    /**
     * 根据信息创建二维码
     *
     * @param content 二维码信息
     * @return 二维码图片二进制
     */
    byte[] createQrCode(String content);

    /**
     * 根据内容与Logo文件生成二维码
     *
     * @param content 二维码信息
     * @param logoFile 图标文件
     * @return 二维码图片
     */
    byte[] createQrCode(String content, File logoFile);

    /**
     * 根据内容与长度尺寸生成二维码
     *
     * @param content 二维码内容信息
     * @param length 二维码尺寸
     * @return 二维码图片
     */
    byte[] createQrCode(String content, int length);

    /**
     * 根据内容、长度尺寸与Logo文件生成二维码
     *
     * @param content 二维码内容
     * @param length 二维码尺寸
     * @param logoFile 二维码图标
     * @return 二维码图片
     */
    byte[] createQrCode(String content, int length, File logoFile);

    /**
     * 解析二维码图片
     *
     * @param image 二维码图片
     * @return 二维码内容
     */
    String decodeImage(File image);
}
