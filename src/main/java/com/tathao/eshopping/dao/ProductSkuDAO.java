package com.tathao.eshopping.dao;

import com.tathao.eshopping.model.entity.ProductSkuEntity;

import java.util.List;


public interface ProductSkuDAO extends GenericDAO<ProductSkuEntity, Long> {

    boolean delete(List<Long> idsDelete);
}
