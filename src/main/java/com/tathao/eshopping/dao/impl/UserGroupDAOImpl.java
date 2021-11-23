package com.tathao.eshopping.dao.impl;

import com.tathao.eshopping.dao.UserGroupDAO;
import com.tathao.eshopping.model.entity.UserGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserGroupDAOImpl extends AbstractHibernateDAO<UserGroupEntity, Long> implements UserGroupDAO {

    @Autowired
    private UserGroupDAO userGroupDAO;

    @Override
    public UserGroupEntity findByCode(String code) {
        UserGroupEntity userGroupEntity = userGroupDAO.findEqualUnique("code", code);
        return userGroupEntity;
    }
}
