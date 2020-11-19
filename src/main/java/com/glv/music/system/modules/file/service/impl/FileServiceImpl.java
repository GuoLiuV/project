package com.glv.music.system.modules.file.service.impl;

import com.glv.music.system.enums.PictureEnum;
import com.glv.music.system.modules.exception.StriveException;
import com.glv.music.system.modules.file.exception.DownloadException;
import com.glv.music.system.modules.file.exception.UploadException;
import com.glv.music.system.modules.file.service.FileService;
import com.glv.music.system.modules.property.StriveProperties;
import com.glv.music.system.modules.rbac.service.impl.RbacUserServiceImpl;
import com.glv.music.system.utils.*;
import org.apache.commons.lang.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 文件上传服务类
 *
 * @author ZHOUXIANG
 */
@Service
@SuppressWarnings("unused")
public class FileServiceImpl implements FileService {

    private final Logger logger = LoggerFactory.getLogger(RbacUserServiceImpl.class);

    @Resource
    private StriveProperties striveProperties;

    @Override
    public String saveUploadFile(MultipartFile file) throws UploadException {
        return saveUploadFile(file, null);
    }

    @Override
    public String saveUploadFile(MultipartFile file, String relativePath) {
        if (ObjectUtils.isNull(file)) {
            throw new NullArgumentException("上传文件对象为空");
        }
        try (InputStream in = file.getInputStream()) {
            String fileName = file.getOriginalFilename();
            fileName = RandomStringUtils.genUUID() + "." + FileUtils.extension(fileName);
            String savePath = striveProperties.getUploadPath();
            if (StringUtils.isNotBlank(relativePath)) {
                savePath = FileUtils.joinPath(savePath, relativePath, fileName);
            } else {
                savePath = FileUtils.joinPath(savePath, fileName);
            }
            if (ObjectUtils.isNull(savePath)) {
                throw new StriveException("上传文件的存储路径未配置");
            }
            try (OutputStream out = new FileOutputStream(savePath)) {
                IOUtils.copy(in, out);
                String rName = FileUtils.joinPath(relativePath, fileName);
                //将window路径转成访问路径
                assert rName != null;
                return rName.replace("\\", "/");
            } catch (Exception ex) {
                throw new UploadException("存储文件失败");
            }
        } catch (Exception e) {
            throw new UploadException(e.getMessage());
        }
    }

    @Override
    public String convertImageToBase64(MultipartFile file) {
        String base64 = null;
        try (InputStream in = file.getInputStream()) {
            String ext = FileUtils.extension(file.getOriginalFilename());
            if (ImageUtils.isImageFile(file.getOriginalFilename())) {
                base64 = "data:image/" + ext + ";base64,";
                base64 += ImageUtils.getImageStr(in);
            }
        } catch (IOException e) {
            logger.info("图片转Base64发生异常：" + e.getLocalizedMessage());
        }
        return base64;
    }

    @Override
    public String imageToBase64(File file) {
        String base64 = null;
        try (InputStream in = new FileInputStream(file)) {
            String ext = FileUtils.extension(file.getName());
            if (ImageUtils.isImageFile(file.getName())) {
                base64 = "data:image/" + ext + ";base64,";
                base64 += ImageUtils.getImageStr(in);
            }
        } catch (IOException e) {
            logger.info("图片转Base64发生异常：" + e.getLocalizedMessage());
        }
        return base64;
    }

    @Override
    public void downloadFile(HttpServletResponse response,
                             String filePath, String fileName) throws DownloadException {
        //文件名编码
        fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.reset();
        // 文件的绝对路径
        String fileAbsolutePath = FileUtils.joinPath(striveProperties.getUploadPath(), filePath);
        try (
                InputStream inputStream = new FileInputStream(Objects.requireNonNull(fileAbsolutePath));
                OutputStream outputStream = new BufferedOutputStream(response.getOutputStream())
        ) {
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            logger.error("文件流下载失败：" + e.getMessage());
            throw new DownloadException(e.getMessage());
        }
    }

    @Override
    public void accessImage(HttpServletResponse response, String filePath) {
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        String imageType = PictureEnum.valueOf(
                FileUtils.extension(filePath).toUpperCase()).type;
        response.setContentType(imageType);

        String fileAbsolutePath = FileUtils.joinPath(
                striveProperties.getUploadPath(), filePath);
        try (
                InputStream inputStream = new FileInputStream(Objects.requireNonNull(fileAbsolutePath));
                OutputStream outputStream = response.getOutputStream()
        ) {
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            logger.error("文件流下载失败：" + e.getMessage());
            throw new DownloadException(e.getMessage());
        }
    }
}
