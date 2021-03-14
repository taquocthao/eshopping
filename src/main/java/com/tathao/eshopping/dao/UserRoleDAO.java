package com.tathao.eshopping.dao;

import com.tathao.eshopping.model.entity.UserRoleEntity;

import java.util.List;

public interface UserRoleDAO extends GenericDAO<UserRoleEntity, Long> {

    List<UserRoleEntity> findByUserId(Long userId);

}
