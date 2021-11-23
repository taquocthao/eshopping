package com.tathao.eshopping.dao;

import com.tathao.eshopping.model.entity.UserGroupEntity;

public interface UserGroupDAO extends GenericDAO<UserGroupEntity, Long> {

    UserGroupEntity findByCode(String code);

}
