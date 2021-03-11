package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.dao.ProductDAO;
import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.model.entity.ProductEntity;
import com.tathao.eshopping.service.ProductService;
import com.tathao.eshopping.ultils.ProductBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems) {
        Object[] result = productDAO.findByProperties(properties, sortExpression, sortDirection, firstItem, maxPageItems, "1 = 1");
        List<ProductDTO> products = new ArrayList<ProductDTO>();
        for(ProductEntity entity : (List<ProductEntity>)result[1]) {
            products.add(ProductBeanUtils.entity2DTO(entity));
        }
        result[1] = products;
        return result;
    }
}
