package com.glv.project.system.modules.response.enums;

/**
 * 响应状态枚举
 *
 * @author ZHOUXIANG
 */
public enum ResponseStatusEnum {

    // 成功
    SUCCESS("00", "success", "成功"),
    // 警告
    WARNING("01", "warning", "警告"),
    // 错误
    FAILURE("02", "failure", "错误"),
    // 没有权限
    NOT_AUTHORITY("03", "notAuthority", "未授权"),
    // 没有认证
    NOT_AUTHENTICATION("04", "notAuthentication", "未认证");

    public String code;
    public String name;
    public String message;

    ResponseStatusEnum(String code, String name, String message) {
        this.code = code;
        this.name = name;
        this.message = message;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
