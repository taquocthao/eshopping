package com.tathao.eshopping.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.tathao.eshopping.ultils.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.tathao.eshopping.dao.GenericDAO;

public abstract class AbstractHibernateDAO <T, ID extends Serializable> implements GenericDAO<T, ID> {

	protected Logger log = Logger.getLogger(getClass());
	
	private Class<T> persistentClass;
	
	@Autowired
    private SessionFactory sessionFactory;

    public AbstractHibernateDAO() {
    	this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
    
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
    
    public Session getCurrentOrOpenSession() {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
        }
        catch (HibernateException ex) {
            log.warn(ex.getMessage());
        }
        if (session == null) {
            session = sessionFactory.openSession();
        }

        return session;
    }
	
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public T findById(ID id) {
		return getCurrentSession().get(persistentClass, id);
	}

	@SuppressWarnings("unchecked")
	public T findEqualUnique(String property, Object value) {
    	try {
			CriteriaBuilder criteriaBuilder = this.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.getPersistentClass());
			Root<T> root = criteriaQuery.from(this.getPersistentClass());
			criteriaQuery.select(root);
			criteriaQuery.where(criteriaBuilder.equal(root.get(property), value));
			Query query = this.getCurrentSession().createQuery(criteriaQuery);
			return (T) query.getSingleResult();
		} catch (Exception e) {
    		e.printStackTrace();
		}
    	return null;
	}

	@SuppressWarnings("unchecked")
	public T findEqualUniqueIgnoreCase(String property, Object value) {
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		Criteria criteria = (Criteria) criteriaBuilder.createQuery(getPersistentClass());
		criteria.add(Restrictions.eq(property, value).ignoreCase());
		return (T) criteria.uniqueResult();
	}

	public T findByIdNoAutoCommit(ID id) {
		T result = (T) getCurrentSession().load(persistentClass, id);
        return result;
	}

	public List<T> findAll() {
		Query query = getCurrentSession().createQuery("SELECT u FROM " + getPersistentClass().getSimpleName() + " u ");
        return query.getResultList();
	}

	public Long countAll() {
		Query query = getCurrentSession().createQuery("SELECT COUNT(u.id) FROM " + getPersistentClass().getSimpleName() + " u ");
        return (Long)query.getSingleResult();
	}

	public List<T> find(int maxResults) {
		Criteria criteria = getCurrentSession()
                .createCriteria(getPersistentClass());
        criteria.setFirstResult(0);
        criteria.setMaxResults(maxResults);
        return criteria.list();
	}

	public List<T> findProperty(String property, Object value) {
		Query query = this.getCurrentSession().createQuery("SELECT u FROM " + getPersistentClass().getSimpleName() + " u WHERE u." + property + " = :property");
		query.setParameter("property", value);
		return query.getResultList();
	}

	public List<T> findProperty(String property, Object value, String sortExpression, String sortOrder) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("FROM ").append(this.getPersistentClass().getSimpleName()).append(" u ");
    	sql.append("WHERE u.").append(property).append(" = :property ");
    	sql.append("ORDER BY u.").append(sortExpression).append(" ").append(sortOrder);

    	Query query = this.getCurrentSession().createQuery(sql.toString());
    	query.setParameter("property", value);

		return query.getResultList();
	}
	
	public T save(T entity) throws DataAccessException {
    	log.info("save " + getPersistentClass().getSimpleName() );
    	this.getCurrentSession().persist(entity);
    	return entity;
	}

	public T update(T entity) throws DataAccessException {
		log.info("update " + getPersistentClass().getSimpleName() );
		this.getCurrentSession().merge(entity);
		return entity;
	}

	public T saveOrUpdate(T entity) throws DataAccessException {
		log.info("save or update " + getPersistentClass().getSimpleName() );
		this.getCurrentSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(T entity) throws DataAccessException {
		log.info("delete " + getPersistentClass().getSimpleName() );
		this.getCurrentSession().delete(entity);
		this.getCurrentSession().flush();
	}

	public void detach(T entity) {
		
	}

	public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limit) {
		try {
			Object[] nameQuery = HibernateUtil.buildNameQuery(this.getPersistentClass(), properties, (Map)null, sortExpression, sortDirection, true, false, (String)null, true);
			String queryString = "select A " + nameQuery[0] + nameQuery[1];
			Query query = this.getCurrentSession().createQuery(queryString);
			if (nameQuery.length == 4) {
				String[] params = (String[])((String[])nameQuery[2]);
				Object[] values = (Object[])((Object[])nameQuery[3]);

				for(int i = 0; i < params.length; ++i) {
					query.setParameter(params[i], values[i]);
				}
			}

			if (offset != null && offset >= 0) {
				query.setFirstResult(offset);
			}

			if (limit != null && limit > 0) {
				query.setMaxResults(limit);
			}

			List<T> res = query.getResultList();
			Object totalItem = 0;
			String queryTotal = "SELECT COUNT(*) " + nameQuery[0];
			Query query2 = this.getCurrentSession().createQuery(queryTotal);
			if (nameQuery.length == 4) {
				String[] params = (String[])((String[])nameQuery[2]);
				Object[] values = (Object[])((Object[])nameQuery[3]);

				for(int i = 0; i < params.length; ++i) {
					query2.setParameter(params[i], values[i]);
				}
			}

			totalItem = query2.getSingleResult();
			return new Object[]{totalItem, res};
		} catch (RuntimeException var16) {
			return new Object[]{0, new ArrayList()};
		}
	}


	public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limit, String whereClause) {
		try {
			Object[] nameQuery = HibernateUtil.buildNameQuery(this.getPersistentClass(), properties, (Map)null, sortExpression, sortDirection, true, false, whereClause, true);
			String queryString = "select A " + nameQuery[0] + nameQuery[1];
			Query query = getCurrentOrOpenSession().createQuery(queryString);
			if (nameQuery.length == 4) {
				String[] params = (String[])((String[])nameQuery[2]);
				Object[] values = (Object[])((Object[])nameQuery[3]);

				for(int i = 0; i < params.length; ++i) {
					query.setParameter(params[i], values[i]);
				}
			}

			if (offset != null && offset >= 0) {
				query.setFirstResult(offset);
			}

			if (limit != null && limit > 0) {
				query.setMaxResults(limit);
			}

			List<T> res = query.getResultList();
			Object totalItem = 0;
			String queryTotal = "SELECT COUNT(*) " + nameQuery[0];
			Query query2 = getCurrentOrOpenSession().createQuery(queryTotal);
			if (nameQuery.length == 4) {
				String[] params = (String[])((String[])nameQuery[2]);
				Object[] values = (Object[])((Object[])nameQuery[3]);

				for(int i = 0; i < params.length; ++i) {
					query2.setParameter(params[i], values[i]);
				}
			}

			totalItem = query2.getSingleResult();
			return new Object[]{totalItem, res};
		} catch (RuntimeException var17) {
			log.error("find all failed", var17);
			return new Object[]{0, new ArrayList()};
		}
	}

}
