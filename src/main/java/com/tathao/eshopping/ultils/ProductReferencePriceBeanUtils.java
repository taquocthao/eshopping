package com.tathao.eshopping.ultils;

import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.model.dto.ProductReferencePriceDTO;
import com.tathao.eshopping.model.entity.ProductEntity;
import com.tathao.eshopping.model.entity.ProductReferencePriceEntity;

public class ProductReferencePriceBeanUtils {

    public static ProductReferencePriceEntity dto2Entity(ProductReferencePriceDTO dto) {
        ProductReferencePriceEntity entity = new ProductReferencePriceEntity();
        entity.setHighestPrice(dto.getHighestPrice());
        entity.setLowestPrice(dto.getLowestPrice());
        entity.setReferencePriceId(dto.getReferencePriceId());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setProduct(new ProductEntity());
        return entity;
    }

    public static ProductReferencePriceDTO entity2DTO(ProductReferencePriceEntity entity) {
        ProductReferencePriceDTO dto = new ProductReferencePriceDTO();
        dto.setHighestPrice(entity.getHighestPrice());
        dto.setLowestPrice(entity.getLowestPrice());
        dto.setReferencePriceId(entity.getReferencePriceId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setProduct(new ProductDTO());
        return dto;
    }
}
