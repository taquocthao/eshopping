package com.tathao.eshopping.dao.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tathao.eshopping.dao.UserDAO;
import com.tathao.eshopping.model.entity.UserEntity;

@Repository
@Transactional
public class UserDAOImpl extends AbstractHibernateDAO<UserEntity, Long> implements UserDAO {
	
	public UserEntity findByUserName(String username) {
		StringBuilder sql = new StringBuilder();
		sql.append("FROM UserEntity u ");
		sql.append("WHERE u.userName = :username");
		
		Query query = getCurrentSession().createQuery(sql.toString());
		query.setParameter("username", username);
		
		return query.getResultList().size() > 0 ? (UserEntity) query.getResultList().get(0) : null;
	}
}
