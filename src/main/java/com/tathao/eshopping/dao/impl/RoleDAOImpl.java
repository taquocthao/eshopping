package com.tathao.eshopping.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tathao.eshopping.dao.RoleDAO;
import com.tathao.eshopping.model.entity.RoleEntity;

import javax.persistence.Query;

@Repository
@Transactional
public class RoleDAOImpl extends AbstractHibernateDAO<RoleEntity, Long> implements RoleDAO{

	public List<RoleEntity> findByUserId(Long userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("from RoleEntity re where re.user.userId = :userId");
		Query query = getCurrentSession().createQuery(sql.toString());
		query.setParameter("userId", userId);

		return query.getResultList();
	}

}
