package com.tathao.eshopping.service;

import com.tathao.eshopping.service.impl.AuthenticationTokenImpl;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TokenAuthenticationService {

    void addAuthentication(HttpServletResponse response, AuthenticationTokenImpl auth);

    Authentication getAuthentication(HttpServletRequest request);

}
