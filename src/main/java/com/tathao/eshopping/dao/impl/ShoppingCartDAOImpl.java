package com.tathao.eshopping.dao.impl;

import com.tathao.eshopping.dao.ShoppingCartDAO;
import com.tathao.eshopping.model.entity.ShoppingCartEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ShoppingCartDAOImpl extends AbstractHibernateDAO<ShoppingCartEntity, Long>  implements ShoppingCartDAO {

    @Override
    public ShoppingCartEntity findByCustomerAndSkuDimension(String customerCode, String skuDimensionCode) {
        return null;
    }
}
