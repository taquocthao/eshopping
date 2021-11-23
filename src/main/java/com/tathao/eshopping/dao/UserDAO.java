package com.tathao.eshopping.dao;

import com.tathao.eshopping.model.entity.UserEntity;

public interface UserDAO extends GenericDAO<UserEntity, String> {
	UserEntity findByUserName(String username);

	UserEntity findByEmail(String email);

    String findAvailableUserName(String userNamePrefix);
}
