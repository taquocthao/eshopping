package com.tathao.eshopping.dao;

import java.util.List;

import com.tathao.eshopping.model.entity.RoleEntity;

public interface RoleDAO extends GenericDAO<RoleEntity, Long> {

	List<RoleEntity> findByUserId(Long userId);

	RoleEntity findByCode(String code);
}
