package com.tathao.eshopping.dao.impl;

import com.tathao.eshopping.dao.ProductDAO;
import com.tathao.eshopping.model.entity.ProductEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Repository
@Transactional
public class ProductDAOImpl extends AbstractHibernateDAO<ProductEntity, Long> implements ProductDAO {

}
