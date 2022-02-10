package com.tathao.eshopping.dao;

import com.tathao.eshopping.model.entity.OrderOutletEntity;

import java.util.Map;

public interface OrderOutletDAO extends GenericDAO<OrderOutletEntity, Long> {

    Integer countTotalOrderByStatus(String status);
}
