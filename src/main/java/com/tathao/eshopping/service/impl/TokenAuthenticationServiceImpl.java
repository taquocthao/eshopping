package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.service.TokenAuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {

    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String SECRET_KEY = "itissimplekey";
    private static final Long EXPIRATION = 86400000L; // 1 day

    @Override
    public void addAuthentication(HttpServletResponse response, AuthenticationTokenImpl auth) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", auth.getPrincipal());
        claims.put("hash", auth.getHash());
        //generate token
        String jwt = Jwts.builder()
                .setSubject(auth.getPrincipal().toString())
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.ES512, SECRET_KEY)
                .compact();
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwt);
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if(token == null) {
            return null;
        }
        token = token.replace(TOKEN_PREFIX, "").trim();
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody();
        if(claims != null && claims.containsKey("username")) {
            String username = claims.get("username").toString();
            String hash = claims.get("hash").toString();

            //todo check with redis

        }

        return null;
    }
}
