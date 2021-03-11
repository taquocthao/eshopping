package com.tathao.eshopping.service;

import com.tathao.eshopping.model.dto.ProductDTO;

import java.util.List;
import java.util.Map;

public interface ProductService {

    Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems);

}
