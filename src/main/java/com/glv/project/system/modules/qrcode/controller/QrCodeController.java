package com.glv.project.system.modules.qrcode.controller;


import com.glv.project.system.modules.qrcode.service.QrCodeService;
import com.glv.project.system.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * 二维码生成与解析
 *
 * @author ZHOUXIANG
 */
@Controller
@RequestMapping("/admin/sys/qr_code")
public class QrCodeController {

    private final Logger logger = LoggerFactory.getLogger(QrCodeController.class);

    @Resource
    private QrCodeService qrCodeService;

    /**
     * 返回二维码图片流，例如，前端使用<img th:href="@{/qr_code/image(content='',length=200)}" />
     */
    @RequestMapping("/image")
    public void getQrCodeImage(HttpServletResponse response, String content, Integer length) {
        try (OutputStream out = response.getOutputStream()) {
            //生成二维码图片
            byte[] imageByte = qrCodeService.createQrCode(content, length);
            //设置response
            response.setDateHeader("Expires", 0L);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");
            response.reset();
            ImageUtils.byte2image(imageByte, out);
        } catch (Exception e) {
            logger.error("输出二维码图片流出错：" + e.getMessage());
        }
    }
}
