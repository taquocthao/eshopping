package com.tathao.eshopping.dao.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tathao.eshopping.dao.UserDAO;
import com.tathao.eshopping.model.entity.UserEntity;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDAOImpl extends AbstractHibernateDAO<UserEntity, String> implements UserDAO {

	public UserEntity findByUserName(String username) {
		StringBuilder sql = new StringBuilder();
		sql.append("FROM UserEntity u ");
		sql.append("WHERE u.username = :username");
		
		Query query = getCurrentSession().createQuery(sql.toString());
		query.setParameter("username", username);
		
		return query.getResultList().size() > 0 ? (UserEntity) query.getResultList().get(0) : null;
	}

	@Override
	public UserEntity findByEmail(String email) {
		StringBuilder sql = new StringBuilder();
		sql.append("FROM UserEntity u ");
		sql.append("WHERE u.email = :email");

		Query query = getCurrentSession().createQuery(sql.toString());
		query.setParameter("email", email);

		return query.getResultList().size() > 0 ? (UserEntity) query.getResultList().get(0) : null;
	}

	@Override
	public String findAvailableUserName(String userNamePrefix) {
		UserEntity userEntity = this.findByUserName(userNamePrefix);
		if(userEntity == null) {
			return userNamePrefix;
		}
		int i = 0;
		while (true) {
			i++;
			String userName = userNamePrefix + "_" + i;
			userEntity = this.findByUserName(userName);
			if(userEntity == null) {
				return userName;
			}
		}
	}
}
