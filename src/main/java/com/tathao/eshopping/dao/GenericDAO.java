package com.tathao.eshopping.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

public interface GenericDAO<T, ID extends Serializable> {

	Session getCurrentSession(); 
	
	public Class<T> getPersistentClass();
	
    public T findById(ID id, boolean lock);

    public T findEqualUnique(final String property, final Object value);

    public T findEqualUniqueIgnoreCase(final String property, final Object value);

    public T findByIdNoAutoCommit(final ID id);

	T save(T entity) throws DataAccessException;

	T update(T entity) throws DataAccessException;

	T saveOrUpdate(T entity) throws DataAccessException;

	void delete(T entity) throws DataAccessException;

    void detach(T entity);

    List<T> findAll();

    public Long countAll();

    List<T> find(int maxResults);
    
    public List<T> findProperty(final String property, final Object value);

    List<T> findProperty(final String property, final Object value, final String sortExpression, final String sortOrder);

    Object[] findByProperties(final Map<String, Object> properties, final String sortExpression, final String sortDirection, final Integer offset, final Integer limit);

    Object[] findByProperties(final Map<String, Object> properties, final String sortExpression, final String sortDirection, final Integer offset, final Integer limit, final String whereClause);
}
