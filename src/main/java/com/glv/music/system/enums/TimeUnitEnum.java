package com.glv.music.system.enums;

/**
 * 时间单位
 *
 * @author ZHOUXIANG
 */
public enum TimeUnitEnum {

    /**
     * 秒
     */
    SECOND(1),

    /**
     * 分
     */
    MINUTE(2),

    /**
     * 时
     */
    HOUR(3),

    /**
     * 日
     */
    DAY(4),

    /**
     * 月
     */
    MONTH(5),

    /**
     * 年
     */
    YEAR(6),

    /**
     * 星期
     */
    WEEK(7),

    /**
     * 星期几
     */
    DAY_OF_WEEK(8),

    /**
     * 一个月中的第几天
     */
    DAY_OF_MONTH(9)
    ;

    public int value;

    TimeUnitEnum(int value) {
        this.value = value;
    }
}
