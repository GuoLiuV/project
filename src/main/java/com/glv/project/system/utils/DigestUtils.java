package com.glv.project.system.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Base64Utils;

/**
 * @author ZHOUXIANG
 */
public class DigestUtils extends Base64 {

    /**
     * 计算md5字符串
     *
     * @param data 原始字符串
     * @return md5加密后的字符串
     */
    public static String md5Hex(String data) {
        return org.apache.commons.codec.digest.DigestUtils.md5Hex(data);
    }

    /**
     * 计算sha1字符串
     *
     * @param data 原始字符串
     * @return sha1字符串
     */
    public static String sha1Hex(String data) {
        return org.apache.commons.codec.digest.DigestUtils.sha1Hex(data);
    }

    /**
     * base64编码
     *
     * @param data 原始字符串
     * @return 编码后的字符串
     */
    public static String enBase64(String data) {
        return Base64Utils.encodeToString(data.getBytes());
    }

    /**
     * base64解码
     *
     * @param data 编码字符串
     * @return 解码后的字符串
     */
    public static String deBase64(String data) {
        return new String(Base64Utils.decodeFromString(data));
    }

    /**
     * sha256加密
     *
     * @param data 数据
     * @return 加密数据
     */
    public static String sha256Hex(String data) {
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(data.getBytes());
    }

}
