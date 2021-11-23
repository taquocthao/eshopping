package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.dao.UserDAO;
import com.tathao.eshopping.dao.UserRoleDAO;
import com.tathao.eshopping.model.dto.UserDTO;
import com.tathao.eshopping.model.entity.UserEntity;
import com.tathao.eshopping.model.entity.UserRoleEntity;
import com.tathao.eshopping.model.social.MySocialUserDetails;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MySocialUserDetailsServiceImpl implements SocialUserDetailsService {

    private static Logger logger = Logger.getLogger(MySocialUserDetailsServiceImpl.class);

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserRoleDAO userRoleDAO;

    // Loads the UserDetails by using the userID of the user.
    // (This method Is used by Spring Security API).
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info(">>>MySocialUserDetailsServiceImpl loadUserByUserId: " + userId);
        UserEntity userEntity = userDAO.findById(userId);
        List<UserRoleEntity> userRoles = userRoleDAO.findByUserId(userEntity.getUserId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        userRoles.forEach(userRole -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(userRole.getRole().getCode());
            authorities.add(authority);
        });
        MySocialUserDetails userDetails = new MySocialUserDetails(userEntity, authorities);
        return userDetails;
    }
}
