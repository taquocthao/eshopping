package com.tathao.eshopping.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.loader.criteria.CriteriaLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.tathao.eshopping.dao.GenericDAO;

public abstract class AbstractHibernateDAO <T, ID extends Serializable> implements GenericDAO<T, ID> {

	protected Logger log = Logger.getLogger(getClass());
	
	private Class<T> persistentClass;
	
	@Autowired
    private SessionFactory sessionFactory;
    private HibernateTemplate hibernateTemplate;
    
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

	public T findById(ID id, boolean lock) {
		T entity = null;
		getCurrentSession().get(persistentClass, id);
		return entity;
	}

	@SuppressWarnings("unchecked")
	public T findEqualUnique(String property, Object value) {
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		Criteria criteria = (Criteria) criteriaBuilder.createQuery(getPersistentClass());
		criteria.add(Restrictions.eq(property, value));
		return (T) criteria.uniqueResult();
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
		return null;
	}

	public List<T> findProperty(String property, Object value, String sortExpression, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public T save(T entity) throws DataAccessException {
		return null;
	}

	public T update(T entity) throws DataAccessException {
		return null;
	}

	public T saveOrUpdate(T entity) throws DataAccessException {
		return null;
	}

	public void delete(T entity) throws DataAccessException {
		
	}

	public void detach(T entity) {
		
	}

}
