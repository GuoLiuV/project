package com.glv.music.system.modules.jpa.dto;

import com.glv.music.system.modules.rbac.entity.RbacUserEntity;
import com.glv.music.system.modules.sysadmin.SysAdminUtils;
import com.glv.music.system.utils.ObjectUtils;
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
