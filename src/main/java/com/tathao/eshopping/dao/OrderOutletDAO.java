package com.tathao.eshopping.dao;

import com.tathao.eshopping.model.entity.OrderOutletEntity;

import java.util.Map;

public interface OrderOutletDAO extends GenericDAO<OrderOutletEntity, Long> {

    Object[] findByPropertiesFetchTotalOrder(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int totalItems, String whereClause);
}
