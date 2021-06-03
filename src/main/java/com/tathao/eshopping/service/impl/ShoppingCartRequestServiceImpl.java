package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.dao.CustomerDAO;
import com.tathao.eshopping.dao.ProductSkuDimensionDAO;
import com.tathao.eshopping.dao.ShoppingCartDAO;
import com.tathao.eshopping.model.dto.ShoppingCartDTO;
import com.tathao.eshopping.model.dto.ShoppingCartRequestDTO;
import com.tathao.eshopping.model.entity.CustomerEntity;
import com.tathao.eshopping.model.entity.ProductSkuDimensionEntity;
import com.tathao.eshopping.model.entity.ShoppingCartEntity;
import com.tathao.eshopping.service.ShoppingCartService;
import com.tathao.eshopping.ultils.mapper.handle.ShoppingCartBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ShoppingCartRequestServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartDAO shoppingCartDAO;
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private ProductSkuDimensionDAO productSkuDimensionDAO;

    @Override
    public ShoppingCartDTO addToCart(ShoppingCartRequestDTO requestDTO) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        ShoppingCartEntity shoppingCartEntity = shoppingCartDAO.findByCustomerAndSkuDimension(requestDTO.getCustomerCode(), requestDTO.getSkuDimensionCode());

        if(shoppingCartEntity != null) {
            shoppingCartEntity.setQuantity(shoppingCartEntity.getQuantity() + requestDTO.getQuantity());
            shoppingCartEntity.setModifiedDate(now);
            shoppingCartEntity = shoppingCartDAO.update(shoppingCartEntity);
        } else {
            shoppingCartEntity = new ShoppingCartEntity();
            shoppingCartEntity.setCreatedDate(now);
            shoppingCartEntity.setModifiedDate(now);
            shoppingCartEntity.setQuantity(requestDTO.getQuantity());
            CustomerEntity customer = customerDAO.findEqualUnique("code", requestDTO.getCustomerCode());
            shoppingCartEntity.setCustomer(customer);
            ProductSkuDimensionEntity productSkuDimension = productSkuDimensionDAO.findEqualUnique("code", requestDTO.getSkuDimensionCode());
            shoppingCartEntity.setSkuDimension(productSkuDimension);
            shoppingCartEntity = shoppingCartDAO.save(shoppingCartEntity);
        }

        return ShoppingCartBeanUtils.entity2DTO(shoppingCartEntity);
    }
}
