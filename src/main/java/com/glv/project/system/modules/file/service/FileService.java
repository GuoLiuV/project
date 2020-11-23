package com.glv.project.system.modules.file.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @author ZHOUXIANG
 */
@SuppressWarnings("unused")
public interface FileService {

    /**
     * 保存上传的文件
     *
     * @param file 上传的文件
     * @return 保存后的文件名
     */
    String saveUploadFile(MultipartFile file);

    /**
     * 指定相对路径存储文件
     *
     * @param file         文件流
     * @param relativePath 相对路径
     * @return 文件路径
     */
    String saveUploadFile(MultipartFile file, String relativePath);

    /**
     * 将通过Spring MVC上传的图片文件转换成Base64
     *
     * @param file 上传的文件
     * @return base64字符串
     */
    String convertImageToBase64(MultipartFile file);

    /**
     * 将图片文件转成Base64字符串
     *
     * @param file 图片文件
     * @return base64字符串
     */
    String imageToBase64(File file);

    /**
     * 以流的方式下载文件
     *
     * @param response 响应
     * @param filePath 文件相对路径
     * @param fileName 为下载的文件指定友好的名称
     */
    void downloadFile(HttpServletResponse response, String filePath, String fileName);

    /**
     * 图片访问
     *
     * @param response 响应流
     * @param filePath 文件相对路径
     */
    void accessImage(HttpServletResponse response, String filePath);
}
