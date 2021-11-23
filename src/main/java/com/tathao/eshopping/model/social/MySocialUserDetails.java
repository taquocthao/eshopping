package com.tathao.eshopping.model.social;

import com.tathao.eshopping.model.dto.UserDTO;
import com.tathao.eshopping.model.entity.RoleEntity;
import com.tathao.eshopping.model.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MySocialUserDetails implements SocialUserDetails {

    private UserEntity userEntity;
    private List<GrantedAuthority> grantedAuthorities;

    public MySocialUserDetails(UserEntity userEntity, List<GrantedAuthority> authorities) {
        this.userEntity = userEntity;
        this.grantedAuthorities = authorities;
    }

    public MySocialUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
        grantedAuthorities = new ArrayList<>();
        List<RoleEntity> roles = userEntity.getRoles();
        for(RoleEntity role : roles) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getCode());
            grantedAuthorities.add(grantedAuthority);
        }
    }

    @Override
    public String getUserId() {
        return userEntity.getUserId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
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
}
