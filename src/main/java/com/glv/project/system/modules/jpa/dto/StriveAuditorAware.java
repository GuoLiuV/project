package com.glv.project.system.modules.jpa.dto;

import com.glv.project.system.modules.rbac.entity.RbacUserEntity;
import com.glv.project.system.modules.sysadmin.SysAdminUtils;
import com.glv.project.system.utils.ObjectUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author ZHOUXIANG
 */
@Component
public class StriveAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        RbacUserEntity userEntity = SysAdminUtils.getCurrentUserEntity();
        if (ObjectUtils.notNull(userEntity)) {
            return Optional.of(userEntity.getId());
        }
        return Optional.of(-1L);
    }
}
