package com.glv.project.system.modules.excel.annotation;

import java.lang.annotation.*;

/**
 * @author ZHOUXIANG
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelCell {

    String name() default "";

    String dateFormat() default "yyyy-MM-dd HH:mm:ss";
}
