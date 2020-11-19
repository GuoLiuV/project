package com.glv.music.system.modules.actionlog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.glv.music.system.modules.jpa.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author ZHOUXIANG
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "T_SYS_LOG_ACTION")
@TableName("T_SYS_LOG_ACTION")
public class ActionLogEntity extends BaseEntity {

    /**
     * 操作人姓名
     */
    private String user;

    /**
     * 操作主题，手动输入，（如：插入等）
     */
    private String title;

    /**
     * 操作值，注解中可以通过String表达式取的值
     * 注：主要针对操作对象的主键，或其它通过SpEL表达式取得的字符串。
     * SpEL需要计算到属性级别
     */
    @Column(columnDefinition = "text")
    private String content;

    /**
     * 操作描述，手动填写
     */
    private String description;

    /**
     * 操作的方法（注解的方法）
     */
    private String method;

    /**
     * 操作状态，是否操作成功，
     *
     * @see OperateStatusEnum
     */
    private String status;

    /**
     * 操作入口，即Request中的请求地址
     */
    private String path;

    /**
     * 操作失败的原因
     */
    private String fail;

}
