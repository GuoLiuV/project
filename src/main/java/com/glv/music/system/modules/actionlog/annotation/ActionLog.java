package com.glv.music.system.modules.actionlog.annotation;

import java.lang.annotation.*;

/**
 * 注解行为日志
 *
 * @author ZHOUXIANG
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ActionLog {

    /**
     * 行为日志标题，如“查询”、“删除”、“更新”、“保存”等
     */
    String title() default "";

    /**
     * 行为日志内容，指定spring表达式请求的数据，如：#{#params.xx}
     * 请参照Spring EL 模板表达式
     */
    String content() default "";

    /**
     * 附加日志描述
     */
    String description() default "";
}
