package com.glv.project.system.modules.security.service;

import com.glv.project.system.modules.rbac.entity.RbacUserEntity;
import com.glv.project.system.modules.rbac.service.RbacUserService;
import com.glv.project.system.modules.security.dto.StriveUserDetails;
import com.glv.project.system.utils.ObjectUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ZHOUXIANG
 */
@Component
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private RbacUserService rbacUserService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        StriveUserDetails user = new StriveUserDetails();
        RbacUserEntity rbacUserEntity = rbacUserService.getUserByLoginName(userName);
        if (ObjectUtils.notNull(rbacUserEntity)) {
            user.setUser(rbacUserEntity);
        }
        return user;
    }

}
