package com.tathao.eshopping.dao;

import com.tathao.eshopping.model.entity.ProductEntity;

import java.util.List;


public interface ProductDAO extends GenericDAO<ProductEntity, Long> {

    boolean updateStatus(List<Long> ids, boolean active);
}
