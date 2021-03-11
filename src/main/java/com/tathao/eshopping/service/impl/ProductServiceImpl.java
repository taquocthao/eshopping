package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    public List<ProductDTO> findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems) {
        return null;
    }
}
