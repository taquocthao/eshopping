package com.tathao.eshopping.dao.impl;

import com.tathao.eshopping.dao.ProductSkuDAO;
import com.tathao.eshopping.model.entity.ProductSkuEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProductSkuDAOImpl extends AbstractHibernateDAO<ProductSkuEntity, Long> implements ProductSkuDAO {

}
