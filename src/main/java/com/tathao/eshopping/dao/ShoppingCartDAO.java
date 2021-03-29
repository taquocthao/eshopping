package com.tathao.eshopping.dao;

import com.tathao.eshopping.model.entity.ShoppingCartEntity;

public interface ShoppingCartDAO extends GenericDAO<ShoppingCartEntity, Long> {

    ShoppingCartEntity findByCustomerAndSku(Long customerId, Long skuId);
}
