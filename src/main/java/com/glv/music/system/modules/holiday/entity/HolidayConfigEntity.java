package com.glv.music.system.modules.holiday.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.glv.music.system.enums.HolidayTypeEnum;
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
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "T_SYS_CONFIG_HOLIDAY")
@TableName("T_SYS_CONFIG_HOLIDAY")
public class HolidayConfigEntity extends BaseEntity {

    /**
     * 节假日年份
     */
    private Integer year;

    /**
     * 日期，格式为2019-08-09
     */
    @Column(columnDefinition = "date", unique = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String date;

    /**
     * 节假日类型，放假或调班
     *
     * @see HolidayTypeEnum
     */
    private String type;
}
