package com.glv.project.system.enums;

/**
 * 框架使用一些常量
 *
 * @author ZHOUXIANG
 */
public enum StriveConstantsEnum {

    /**
     * 配置spring security角色前缀，可以改为AUTH_，
     * 只能在应用开发之前修改，开发后建议不要动
     */
    AUTH_PREFIX("ROLE_"),

    /**
     * 手动插入用户时默认的密码，123456的md5编码
     */
    DEFAULT_PASSWORD("e10adc3949ba59abbe56e057f20f883e"),

    /**
     * 框架系统平台使用的APP_ID
     */
    STRIVE_APP_ID("strive_app_id_sysplatform"),

    /**
     * 框架系统平台使用的APP_SECRET
     */
    STRIVE_APP_SECRET("strive_app_secret_sysplatform");


    public String value;

    StriveConstantsEnum(String value) {
        this.value = value;
    }
}
