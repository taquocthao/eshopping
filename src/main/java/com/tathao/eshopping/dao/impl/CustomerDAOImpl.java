package com.tathao.eshopping.dao.impl;

import com.tathao.eshopping.dao.CustomerDAO;
import com.tathao.eshopping.model.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAOImpl extends AbstractHibernateDAO<CustomerEntity, Long> implements CustomerDAO {


}
