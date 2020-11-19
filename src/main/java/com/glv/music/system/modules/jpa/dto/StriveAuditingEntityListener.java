package com.glv.music.system.modules.jpa.dto;

import com.glv.music.system.modules.jpa.entity.BaseEntity;
import com.glv.music.system.modules.rbac.entity.RbacUserEntity;
import com.glv.music.system.modules.sysadmin.SysAdminUtils;
import com.glv.music.system.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * @author ZHOUXIANG
 */
@Slf4j
public class StriveAuditingEntityListener {

    @PrePersist
    public void prePersist(BaseEntity entity){
        RbacUserEntity userEntity = SysAdminUtils.getCurrentUserEntity();
        if (ObjectUtils.notNull(userEntity)) {
            entity.setCreateUserId(userEntity.getId());
        }else {
            entity.setCreateUserId(0L);
        }
        log.info("开始保存--{}", entity.toString());
    }

    @PreUpdate
    public void preUpdate(BaseEntity entity){
        RbacUserEntity userEntity = SysAdminUtils.getCurrentUserEntity();
        if (ObjectUtils.notNull(userEntity)) {
            entity.setUpdateUserId(userEntity.getId());
        }else {
            entity.setUpdateUserId(0L);
        }
        log.info("开始更新--", entity.toString());
    }

    @PostPersist
    public void postPersist(BaseEntity entity){
        log.info("结束保存--", entity.toString());
    }

    @PostUpdate
    public void postUpdate(BaseEntity entity){
        log.info("结束更新--", entity.toString());
    }

}
