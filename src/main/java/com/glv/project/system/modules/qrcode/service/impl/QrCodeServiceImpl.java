package com.glv.project.system.modules.qrcode.service.impl;

import com.glv.project.system.modules.property.StriveProperties;
import com.glv.project.system.modules.qrcode.service.QrCodeService;
import com.glv.project.system.utils.FileUtils;
import com.glv.project.system.utils.IOUtils;
import com.glv.project.system.utils.QRCodeUtils;
import com.glv.project.system.utils.RandomStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Objects;

/**
 * 二维码生成服务类
 *
 * @author ZHOUXIANG
 */
@Service
@Slf4j
@SuppressWarnings("unused")
public class QrCodeServiceImpl implements QrCodeService {

    @Resource
    private StriveProperties striveProperties;

    @Override
    public byte[] createQrCode(String content) {
        return QRCodeUtils.createQrcode(content, null);
    }

    @Override
    public byte[] createQrCode(String content, File logoFile) {
        return QRCodeUtils.createQrcode(content, logoFile);
    }

    @Override
    public byte[] createQrCode(String content, int length) {
        return QRCodeUtils.createQrcode(content, length, null);
    }

    @Override
    public byte[] createQrCode(String content, int length, File logoFile) {
        return QRCodeUtils.createQrcode(content, length, logoFile);
    }

    @Override
    public String decodeImage(File image) {
        String code = null;
        try {
            code = QRCodeUtils.decodeQrcode(image);
        } catch (Exception e) {
            log.error("二维码解析出错：{}", e.getMessage());
        }
        return code;
    }

    /**
     * 解析图片输入流返回二维码信息字符串，提供流的原因是前端拍照上传的是流，就直接解析了
     *
     * @param inputStream 图片流
     * @return base64
     */
    public String decodeImage(InputStream inputStream) {
        String code = null;
        try {
            String name = RandomStringUtils.genUUID() + ".jpg";
            File file = new File(Objects.requireNonNull(FileUtils.joinPath(striveProperties.getTempPath(), name)));
            IOUtils.copy(inputStream, new FileOutputStream(file));
            code = QRCodeUtils.decodeQrcode(file);
            file.deleteOnExit();
            return code;
        } catch (Exception e) {
            log.error("二维码解析出错：{}", e.getMessage());
        }
        return code;
    }
}
