package com.tathao.eshopping.service;

import java.util.Map;

public interface ProductSkuService {

    Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems);

}
