package com.glv.music.system.modules.file.controller;


import com.glv.music.system.modules.file.service.impl.FileServiceImpl;
import com.glv.music.system.modules.request.annotation.RestPostMapping;
import com.glv.music.system.modules.response.dto.ResponseStatusDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件上传控制器
 *
 * @author ZHOUXIANG
 */
@Controller
@Slf4j
@Api
@RequestMapping("/admin/sys/file")
public class FileController {

    @Resource
    private FileServiceImpl fileService;

    /**
     * 上传文件并保存
     */
    @ApiOperation("文件上传")
    @RestPostMapping("/upload")
    public ResponseStatusDto<String> uploadFile(MultipartFile file) {
        ResponseStatusDto<String> responseStatusDto = ResponseStatusDto.failure();
        try {
            String fileName = fileService.saveUploadFile(file);
            responseStatusDto = ResponseStatusDto.success(fileName);
        } catch (Exception e) {
            log.error("文件上传失败", e);
        }
        return responseStatusDto;
    }

    /**
     * 上传图片不保存，将图片二进制转成base64编码返回
     */
    @ApiOperation("将图片转成base64后返回")
    @RestPostMapping("/image/base64")
    public ResponseStatusDto<String> uploadImage(MultipartFile image) {
        ResponseStatusDto<String> responseStatusDto = ResponseStatusDto.failure();
        try {
            String base64 = fileService.convertImageToBase64(image);
            responseStatusDto = ResponseStatusDto.success(base64);
        } catch (Exception e) {
            log.error("图片上传异常", e);
        }
        return responseStatusDto;
    }

    /**
     * 文件下载，此方法不用返回任务内容，因为下载的文件内容已经流入response输出流，
     * 再返回的话，文件内容就不对了。
     */
    @ApiOperation("文件下载")
    @GetMapping("/download")
    public void downloadFile(HttpServletResponse response, String filePath, String fileName) {
        try {
            fileService.downloadFile(response, filePath, fileName);
        } catch (Exception e) {
            log.error("文件下载失败", e);
        }
    }

    @ApiOperation("图片访问")
    @GetMapping("/images/**")
    public void accessImage(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getServletPath();
            filePath = filePath.replace("/file/images", "");
            fileService.accessImage(response, filePath);
        } catch (Exception e) {
            log.error("图片访问失败", e);
        }
    }

}
