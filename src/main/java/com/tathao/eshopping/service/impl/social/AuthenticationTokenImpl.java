package com.tathao.eshopping.service.impl.social;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticationTokenImpl extends AbstractAuthenticationToken {

    public AuthenticationTokenImpl(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    //todo: getHash
    public String getHash() {
        return null;
    }
}
