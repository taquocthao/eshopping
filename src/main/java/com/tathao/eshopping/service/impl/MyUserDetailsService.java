package com.tathao.eshopping.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tathao.eshopping.dao.RoleDAO;
import com.tathao.eshopping.dao.UserDAO;
import com.tathao.eshopping.model.entity.RoleEntity;
import com.tathao.eshopping.model.entity.UserEntity;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	RoleDAO roleDAO;
	
	public UserDetails loadUserByUsername(String username) {
		UserEntity user = userDAO.findByUserName(username);
		if(user == null) {
			throw new UsernameNotFoundException("User " + username + " was not found in the database.");
		}
		
		List<RoleEntity> roles = roleDAO.findByUserId(user.getUserId());
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if(roles != null) {
			for(RoleEntity role : roles) {
				GrantedAuthority authority = new SimpleGrantedAuthority(role.getCode());
				authorities.add(authority);
				
			}
		}
		
		UserDetails userDetails = new User(user.getUsername(), user.getPassword(), authorities);
		return userDetails;
	}

}
