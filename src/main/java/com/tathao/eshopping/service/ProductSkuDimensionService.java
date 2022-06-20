package com.tathao.eshopping.service;

import java.util.Map;

public interface ProductSkuDimensionService {

    Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems, String whereClause);

}
