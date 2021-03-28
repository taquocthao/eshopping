package com.tathao.eshopping.dao.impl;

import com.tathao.eshopping.dao.UserRoleDAO;
import com.tathao.eshopping.model.entity.UserRoleEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRoleDAOImpl extends AbstractHibernateDAO<UserRoleEntity, Long> implements UserRoleDAO {

    @Override
    public List<UserRoleEntity> findByUserId(Long userId) {
        StringBuilder sql = new StringBuilder();
        sql.append("from UserRoleEntity ur where ur.user.userId = :userId");
        Query query = getCurrentSession().createQuery(sql.toString());
        query.setParameter("userId", userId);

        return query.getResultList();
    }
}
