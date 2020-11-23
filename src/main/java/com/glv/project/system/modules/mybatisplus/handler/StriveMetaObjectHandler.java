package com.glv.project.system.modules.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.glv.project.system.modules.rbac.entity.RbacUserEntity;
import com.glv.project.system.modules.sysadmin.SysAdminUtils;
import com.glv.project.system.utils.ObjectUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatis plus 插入或更新对象时，自动添充一些值
 *
 * @author ZHOUXIANG
 */
@Component
@SuppressWarnings("unused")
public class StriveMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入时也要插入更新字段
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.fillStrategy(metaObject,"createTime", new Date());
        this.fillStrategy(metaObject, "updateTime", new Date());
        RbacUserEntity userEntity = SysAdminUtils.getCurrentUserEntity();
        if (ObjectUtils.notNull(userEntity)) {
            this.fillStrategy(metaObject, "createUserId", userEntity.getId());
            this.fillStrategy(metaObject, "updateUserId", userEntity.getId());
        } else {
            this.fillStrategy(metaObject, "createUserId", 0L);
            this.fillStrategy(metaObject, "updateUserId", 0L);
        }
    }

    /**
     * 更新时只更新更新字段
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.fillStrategy(metaObject, "updateTime", new Date());
        RbacUserEntity userEntity = SysAdminUtils.getCurrentUserEntity();
        if (ObjectUtils.notNull(userEntity)) {
            this.fillStrategy(metaObject, "updateUserId", userEntity.getId());
        } else {
            this.fillStrategy(metaObject, "updateUserId", 0L);
        }
    }
}
