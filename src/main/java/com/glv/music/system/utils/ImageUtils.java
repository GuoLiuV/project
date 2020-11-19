package com.glv.music.system.utils;

import com.glv.music.system.enums.PictureEnum;
import com.glv.music.system.modules.exception.StriveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author ZHOUXIANG
 */
public final class ImageUtils {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    private static final String[] CHARS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E",
            "F", "G", "H", "J", "K", "L", "M", "N", "P"};
    /**
     * 字符长度
     */
    private static final int SIZE = 5;
    /**
     * 干扰线
     */
    private static final int LINES = 7;
    private static final int WIDTH = 100;
    private static final int HEIGHT = 35;
    /**
     * 字体大小
     */
    private static final int FONT_SIZE = 20;

    public static Map<String, BufferedImage> createImage() {
        StringBuilder sb = new StringBuilder();
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = image.getGraphics();
        graphic.setColor(Color.LIGHT_GRAY);
        graphic.fillRect(0, 0, WIDTH, HEIGHT);
        Random ran = new Random();

        for (int i = 1; i <= SIZE; i++) {
            int r = ran.nextInt(CHARS.length);
            graphic.setColor(getRandomColor());
            graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
            graphic.drawString(CHARS[r], (i - 1) * WIDTH / SIZE, HEIGHT / 2 + 5);
            sb.append(CHARS[r]);
        }

        for (int i = 1; i <= LINES; i++) {
            graphic.setColor(getRandomColor());
            graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT), ran.nextInt(WIDTH), ran.nextInt(HEIGHT));
        }
        Map<String, BufferedImage> map = new HashMap<>(1);
        map.put(sb.toString(), image);
        return map;
    }

    /**
     * 获取随机色
     */
    private static Color getRandomColor() {
        Random ran = new Random();
        return new Color(ran.nextInt(156), ran.nextInt(156), ran.nextInt(156));
    }

    /**
     * 将图片进行Base64编码返回字符串
     */
    public static String getImageStr(String imgFilePath) {
        byte[] data = null;
        try (InputStream in = new FileInputStream(imgFilePath)) {
            data = new byte[in.available()];
            int num = in.read(data);
            if (0 == num) {
                throw new StriveException("读取0字节数据");
            }
        } catch (IOException e) {
            logger.error("图片转Base64出错：" + e.getMessage());
        }
        if (ObjectUtils.isNull(data)) {
            return null;
        }
        return Base64Utils.encodeToString(data);
    }

    /**
     * 将图片流转成Base64字符串
     */
    public static String getImageStr(InputStream image) {
        byte[] data = null;
        try {
            data = new byte[image.available()];
            int num = image.read(data);
            if (0 == num) {
                throw new StriveException("读取0字节数据");
            }
        } catch (IOException e) {
            logger.error("图片转Base64出错：" + e.getMessage());
        }
        if (ObjectUtils.isNull(data)) {
            return null;
        }
        return Base64Utils.encodeToString(data);
    }

    /**
     * 将Base64编码的字符串转成图片
     */
    public static void generateImage(String imgStr, File file) {
        try (
                OutputStream out = new FileOutputStream(file)
        ) {
            byte[] bytes = Base64Utils.decodeFromString(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }
            out.write(bytes);
            out.flush();
        } catch (Exception e) {
            logger.error("Base64转图片文件出错：" + e.getMessage());
        }
    }

    /**
     * 判断文件是否为常见图片格式文件
     */
    public static boolean isImageFile(String fileName) {
        String ext = FileUtils.extension(fileName);
        boolean isImage = false;
        if (StringUtils.isNotBlank(ext)) {
            for (PictureEnum value : PictureEnum.values()) {
                if (ext.contains(value.code)) {
                    isImage = true;
                    break;
                }
            }
        }
        return isImage;
    }

    /**
     * 将Image转成字节数组
     */
    public static byte[] image2byte(String path) {
        byte[] data = null;
        try (
                FileImageInputStream input = new FileImageInputStream(new File(path));
                ByteArrayOutputStream output = new ByteArrayOutputStream()
        ) {
            data = readStreamToByte(input, output);
        } catch (Exception e) {
            logger.error("图片转字节串出错：" + e.getMessage());
        }
        return data;
    }

    /**
     * 图片文件输入流转字节数组
     */
    public static byte[] image2byte(ImageInputStream inputStream) {
        byte[] data = null;
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            data = readStreamToByte(inputStream, output);
        } catch (Exception e) {
            logger.error("图片转字节串出错：" + e.getMessage());
        }
        return data;
    }

    /**
     * 将字节数组转成图片文件
     */
    public static void byte2image(byte[] data, String path) {
        try (FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path))) {
            imageOutput.write(data, 0, data.length);
            imageOutput.flush();
        } catch (Exception e) {
            logger.error("字节转图片出错：" + e.getMessage());
        }
    }

    /**
     * 将字节数组转成图片输出流
     */
    public static void byte2image(byte[] data, OutputStream imageOutput) {
        try {
            imageOutput.write(data, 0, data.length);
            imageOutput.flush();
        } catch (Exception e) {
            logger.error("字节转图片出错：" + e.getMessage());
        }
    }

    /**
     * 将输入流发送到输出流
     */
    private static byte[] readStreamToByte(ImageInputStream inputStream,
                                           ByteArrayOutputStream outputStream) throws IOException {
        byte[] buf = new byte[1024];
        int numBytesRead;
        while ((numBytesRead = inputStream.read(buf)) != -1) {
            outputStream.write(buf, 0, numBytesRead);
        }
        return outputStream.toByteArray();
    }

}