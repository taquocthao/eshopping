package com.tathao.eshopping.dao;

import com.tathao.eshopping.model.entity.ShoppingCartEntity;

public interface ShoppingCartDAO extends GenericDAO<ShoppingCartEntity, Long> {

    ShoppingCartEntity findByCustomerAndSkuDimension(String customerCode, String skuDimensionCode);
}
