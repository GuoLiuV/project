package com.glv.music.system.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.glv.music.system.modules.jpa.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统用户实体类
 *
 * @author ZHOUXIANG
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "T_SYS_RBAC_USER")
@TableName("T_SYS_RBAC_USER")
public class RbacUserEntity extends BaseEntity {

    /**
     * 用户登录名
     */
    @Column(unique = true)
    private String loginName;

    /**
     * 密码，插入时给个默认值
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户代码
     */
    private String userCode;

    /**
     * 身份证件ID
     */
    private String cardId;

    /**
     * 性别 MALE/FEMALE
     */
    private String sexCode;

    /**
     * 性别，中文 男女
     */
    private String sexName;

    /**
     * 住址
     */
    private String address;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 身份认证Key的ID
     */
    private String keyId;

    /**
     * 手机设备ID
     */
    private String mobileDeviceId;

    /**
     * Pad设备ID
     */
    private String padDeviceId;

    /**
     * 小程序的openId
     */
    private String proOpenId;

    /**
     * 公众号openId
     */
    private String pubOpenId;

    /**
     * 微信用户统一ID
     */
    private String unionId;

    /**
     * 图像地址
     */
    private String avatar;

    /**
     * 用户所属组织的ID，
     * 一个用户只能属于一个组织
     * 没有组织就是-1，独立用户
     */
    private Long orgId = -1L;

    /**
     * 是否是租户，使用 Y 和 N 标记
     * @see com.glv.music.system.enums.OperateStatusEnum
     */
    private String isTenant = "N";

    /**
     * 序号
     */
    private Double sequence;

}
