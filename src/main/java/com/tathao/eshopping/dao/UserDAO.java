package com.tathao.eshopping.dao;

import com.tathao.eshopping.model.entity.UserEntity;

public interface UserDAO {
	UserEntity findByUserName(String username);
}
