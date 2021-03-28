package com.tathao.eshopping.dao.impl;

import com.tathao.eshopping.dao.ProductDAO;
import com.tathao.eshopping.model.entity.ProductEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAOImpl extends AbstractHibernateDAO<ProductEntity, Long> implements ProductDAO {

}
