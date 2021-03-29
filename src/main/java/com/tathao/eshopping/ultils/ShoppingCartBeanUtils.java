package com.tathao.eshopping.ultils;

import com.tathao.eshopping.model.dto.ShoppingCartDTO;
import com.tathao.eshopping.model.entity.ShoppingCartEntity;

public class ShoppingCartBeanUtils {

    public static ShoppingCartDTO entity2DTO(ShoppingCartEntity entity) {
        if(entity == null) {
            return null;
        }
        ShoppingCartDTO dto = new ShoppingCartDTO();
        dto.setShoppingCartId(entity.getShoppingCartId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setQuantity(entity.getQuantity());
        dto.setCustomer(CustomerBeanUtils.entity2DTO(entity.getCustomer()));
        dto.setSku(ProductSkuBeanUtils.entity2DTO(entity.getSku()));
        return dto;
    }

}
