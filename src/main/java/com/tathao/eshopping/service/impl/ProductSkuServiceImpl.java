package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.dao.ProductSkuDAO;
import com.tathao.eshopping.model.dto.ProductSkuDTO;
import com.tathao.eshopping.model.entity.ProductSkuEntity;
import com.tathao.eshopping.service.ProductSkuService;
import com.tathao.eshopping.ultils.ProductSkuBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductSkuServiceImpl implements ProductSkuService {

    @Autowired
    private ProductSkuDAO productSkuDAO;

    public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems) {
        Object[] result =  productSkuDAO.findByProperties(properties, sortExpression, sortDirection, firstItem, maxPageItems, "1 = 1");

        List<ProductSkuDTO> dtos = new ArrayList<ProductSkuDTO>();
        for(ProductSkuEntity skuEntity : (List<ProductSkuEntity>)result[1]) {
            dtos.add(ProductSkuBeanUtils.entity2DTO(skuEntity));
        }
        result[1] = dtos;
        return result;
    }
}
