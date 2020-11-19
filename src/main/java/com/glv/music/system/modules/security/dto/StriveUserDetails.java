package com.glv.music.system.modules.security.dto;

import com.glv.music.system.modules.rbac.entity.RbacUserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
@Getter
@Setter
@Slf4j
@SuppressWarnings("unused")
public class StriveUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private RbacUserEntity user = new RbacUserEntity();

    private List<GrantedAuthority> authorities = new ArrayList<>();

    public StriveUserDetails() {
    }

    public StriveUserDetails(String userName, String password) {
        this.user.setLoginName(userName);
        this.user.setPassword(password);
    }

    public RbacUserEntity getUser() {
        return user;
    }

    public void setUser(RbacUserEntity user) {
        this.user = user;
    }

    public void setUsername(String username) {
        this.user.setLoginName(username);
    }

    public void setPassword(String password) {
        this.user.setPassword(password);
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public List<GrantedAuthority> getStriveAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLoginName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StriveUserDetails) {
            StriveUserDetails u = (StriveUserDetails) obj;
            return this.toString().equals(u.toString());
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.user.getLoginName().hashCode();
    }

    @Override
    public String toString() {
        return this.user.getLoginName();
    }

}
