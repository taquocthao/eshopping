package com.tathao.eshopping.dao.impl;

import com.tathao.eshopping.dao.OrderOutletDAO;
import com.tathao.eshopping.model.entity.OrderOutletEntity;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class OrderOutletDAOImpl extends AbstractHibernateDAO<OrderOutletEntity, Long> implements OrderOutletDAO {

    @Override
    public Object[] findByPropertiesFetchTotalOrder(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int totalItems, String whereClause) {
        StringBuilder sql = new StringBuilder();

        return new Object[0];
    }
}
