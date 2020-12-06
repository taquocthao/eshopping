package com.tathao.eshopping.dao;

import java.util.List;

import com.tathao.eshopping.model.entity.RoleEntity;

public interface RoleDAO {

	List<RoleEntity> findByUserId(Long userId);
	
}
