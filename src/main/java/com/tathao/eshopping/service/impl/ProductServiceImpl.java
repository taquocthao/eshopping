package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.dao.ProductDAO;
import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.model.entity.ProductEntity;
import com.tathao.eshopping.service.ProductService;
import com.tathao.eshopping.ultils.mapper.handle.ProductBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Override
    public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems) {
        return this.findByProperties(properties, sortExpression, sortDirection, firstItem, maxPageItems, "1 = 1");
    }

    @Override
    public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems, String whereClause) {
        Object[] result = productDAO.findByProperties(properties, sortExpression, sortDirection, firstItem, maxPageItems, whereClause);
        List<ProductDTO> products = new ArrayList<ProductDTO>();
        for(ProductEntity entity : (List<ProductEntity>)result[1]) {
            products.add(ProductBeanUtils.entity2DTO(entity));
        }
        result[1] = products;
        return result;
    }

    @Override
    public ProductDTO findByCode(String code) {
        ProductEntity productEntity = productDAO.findEqualUnique("code", code);
        return ProductBeanUtils.entity2DTO(productEntity);
    }

    @Override
    public ProductDTO findByCodeAndFetchRelatedProducts(String code) {
        ProductEntity productEntity = productDAO.findEqualUnique("code", code);
        // Get related products
        Map<String, Object> properties = new HashMap<>();
        properties.put("catGroup.catGroupId", productEntity.getCatGroup().getCatGroupId());
        Object[] relatedProducts = productDAO.findByProperties(properties, "createdDate", "1", null, 5);
        return ProductBeanUtils.entity2DTOAndFetchRelatedProducts(productEntity, (List<ProductEntity>) relatedProducts[1]);
    }
}
