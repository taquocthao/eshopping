package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.dao.ProductSkuDimensionDAO;
import com.tathao.eshopping.model.dto.ProductSkuDimensionDTO;
import com.tathao.eshopping.model.entity.ProductSkuDimensionEntity;
import com.tathao.eshopping.service.ProductSkuDimensionService;
import com.tathao.eshopping.ultils.mapper.handle.ProductSkuDimensionBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductSkuDimensionServiceImpl implements ProductSkuDimensionService {

    @Autowired
    private ProductSkuDimensionDAO productSkuDimensionDAO;

    @Override
    public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems, String whereClause) {
        Object[] result = productSkuDimensionDAO.findByProperties(properties, sortExpression, sortDirection, firstItem, maxPageItems, whereClause);
        List<ProductSkuDimensionDTO> products = new ArrayList<ProductSkuDimensionDTO>();
        for(ProductSkuDimensionEntity entity : (List<ProductSkuDimensionEntity>)result[1]) {
            products.add(ProductSkuDimensionBeanUtils.entity2DTO(entity));
        }
        result[1] = products;
        return result;
    }
}
