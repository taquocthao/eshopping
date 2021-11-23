package com.tathao.eshopping.service.impl.social;

import java.util.ArrayList;
import java.util.List;

import com.tathao.eshopping.dao.UserRoleDAO;
import com.tathao.eshopping.model.social.MySocialUserDetails;
import com.tathao.eshopping.model.entity.UserRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.stereotype.Service;

import com.tathao.eshopping.dao.UserDAO;
import com.tathao.eshopping.model.entity.UserEntity;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
    private UserRoleDAO userRoleDAO;

	@Override
	public UserDetails loadUserByUsername(String username) {
		UserEntity user = userDAO.findByUserName(username);
		if(user == null) {
			throw new UsernameNotFoundException("User " + username + " was not found in the database.");
		}
		
		List<UserRoleEntity> userRoles = userRoleDAO.findByUserId(user.getUserId());
		List<GrantedAuthority> authorities = new ArrayList<>();
		userRoles.forEach(userRole -> {
			GrantedAuthority authority = new SimpleGrantedAuthority(userRole.getRole().getCode());
			authorities.add(authority);
		});

		SocialUserDetails userDetails = new MySocialUserDetails(user, authorities);
		return userDetails;
	}

}
