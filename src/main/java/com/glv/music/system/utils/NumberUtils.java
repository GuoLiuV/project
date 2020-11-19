package com.glv.music.system.utils;

import java.math.BigDecimal;

/**
 * @author ZHOUXIANG
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {

    /**
     * 四舍五入保留scale位小数
     * @param num 数字
     * @param scale 精度
     * @return 数字
     */
    public static double scaleNumber(double num, int scale) {
        BigDecimal bigDecimal = new BigDecimal(num);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
