package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.dao.ShoppingCartDAO;
import com.tathao.eshopping.model.dto.ShoppingCartDTO;
import com.tathao.eshopping.model.dto.ShoppingCartRequestDTO;
import com.tathao.eshopping.model.entity.CustomerEntity;
import com.tathao.eshopping.model.entity.ProductSkuEntity;
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

    @Override
    public ShoppingCartDTO addToCart(ShoppingCartRequestDTO requestDTO) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        ShoppingCartEntity shoppingCartEntity = shoppingCartDAO.findByCustomerAndSku(requestDTO.getCustomerId(), requestDTO.getSkuId());

        if(shoppingCartEntity != null) {
            shoppingCartEntity.setQuantity(shoppingCartEntity.getQuantity() + requestDTO.getQuantity());
            shoppingCartEntity.setModifiedDate(now);
            shoppingCartEntity = shoppingCartDAO.update(shoppingCartEntity);
        } else {
            shoppingCartEntity = new ShoppingCartEntity();
            shoppingCartEntity.setCreatedDate(now);
            shoppingCartEntity.setModifiedDate(now);
            shoppingCartEntity.setQuantity(requestDTO.getQuantity());
            shoppingCartEntity.setCustomer(new CustomerEntity(requestDTO.getCustomerId()));
            shoppingCartEntity.setSku(new ProductSkuEntity(requestDTO.getSkuId()));
            shoppingCartEntity = shoppingCartDAO.save(shoppingCartEntity);
        }

        return ShoppingCartBeanUtils.entity2DTO(shoppingCartEntity);
    }
}
