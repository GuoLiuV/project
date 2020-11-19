package com.glv.music.system.enums;


import com.glv.music.system.utils.StringUtils;

/**
 * 性别代码
 *
 * @author ZHOUXIANG
 */
public enum SexEnum {
    /**
     * 男
     */
    MALE("男"),
    /**
     * 女
     */
    FEMALE("女"),

    /**
     * 未知
     */
    UNKNOWN("未知");

    public String value;

    SexEnum(String value) {
        this.value = value;
    }

    public static String getValueByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            return SexEnum.valueOf(name).value;
        }
        return null;
    }
}
