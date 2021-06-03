package com.tathao.eshopping.dao.impl;

import com.tathao.eshopping.dao.ProductSkuDimensionDAO;
import com.tathao.eshopping.model.entity.ProductSkuDimensionEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ProductSkuDimensionDAOImpl extends AbstractHibernateDAO<ProductSkuDimensionEntity, Long> implements ProductSkuDimensionDAO {
}
