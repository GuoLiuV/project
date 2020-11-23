package com.glv.project.system.enums;

/**
 * @author ZHOUXIANG
 */
public enum OperateStatusEnum {

    /**
     * YES的标志
     */
    Y,
    /**
     * NO的标志
     */
    N,
    /**
     * REJECT标志
     */
    R;

    /**
     * 判断字符串是否是 Y
     */
    public static boolean isY(String name) {
        return OperateStatusEnum.Y.name().equals(name);
    }

    /**
     * 判断字符串是否是 N
     */
    public static boolean isN(String name) {
        return OperateStatusEnum.N.name().equals(name);
    }

    /**
     * 判断字符串是否是 R
     */
    public static boolean isR(String name) {
        return OperateStatusEnum.R.name().equals(name);
    }
}
