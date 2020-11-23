package com.glv.project.system.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @author ZHOUXIANG
 */
@SuppressWarnings("unused")
public class RandomStringUtils extends org.apache.commons.lang3.RandomStringUtils {

    private static final String[] CHARS = {"0", "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K",
            "L", "M", "N", "P"};

    private static final String[] NUMS = {"0", "1", "2", "3", "4", "5", "6",
            "7", "8", "9"};

    private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

    /**
     * 生成UUID
     */
    public static String genUUID() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }

    /**
     * 用于生成数字随机字符串
     */
    public static String authCode(int len) {
        Random random = new Random();
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < len; i++) {
            buff.append(NUMS[random.nextInt(NUMS.length)]);
        }
        return buff.toString();
    }

    /**
     * 用于生成数字加字母的随机字符串，不区分大小写
     */
    public static String randomCode(int len) {
        Random random = new Random();
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < len; i++) {
            buff.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return buff.toString();
    }

    /**
     * 用于生成数字加字母的随机字符串，区分大小写
     */
    public static String randomString(int len) {
        Random random = new Random();
        if (len < 1) {
            return null;
        }
        char[] randBuffer = new char[len];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[random.nextInt(61)];
        }
        return new String(randBuffer);
    }

}
