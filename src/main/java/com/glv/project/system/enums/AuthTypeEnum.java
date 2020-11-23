package com.glv.project.system.enums;

/**
 * @author ZHOUXIANG
 */
public enum AuthTypeEnum {

    /**
     * 菜单权限
     */
    MENU("菜单权限"),

    /**
     * 元素权限
     */
    ELEMENT("元素权限"),

    /**
     * 资源权限
     */
    RESOURCE("资源权限");

    public String desc;

    AuthTypeEnum(String desc) {
        this.desc = desc;
    }

    /**
     * 根据枚举代码获取名称
     * @param code 代码
     * @return 名称
     */
    public static String getAuthTypeNameByCode(String code) {
        AuthTypeEnum authTypeEnum = AuthTypeEnum.valueOf(code);
        return authTypeEnum.desc;
    }
}
