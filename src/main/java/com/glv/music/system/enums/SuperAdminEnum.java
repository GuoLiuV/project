package com.glv.music.system.enums;

/**
 * @author ZHOUXIANG
 */
public enum SuperAdminEnum {

    /**
     * 超级管理员登录名
     */
    LOGIN_NAME("superadmin"),
    /**
     * 超级管理员登录密码,123456
     */
    PASSWORD("e10adc3949ba59abbe56e057f20f883e"),
    /**
     * 超级管理员权限
     */
    AUTH("SUPERADMIN"),

    /**
     * 超级管理员昵称
     */
    NICK_NAME("超级管理员");

    public String value;

    SuperAdminEnum(String value) {
        this.value = value;
    }
}
