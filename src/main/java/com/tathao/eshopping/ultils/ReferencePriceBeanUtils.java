package com.tathao.eshopping.ultils;

import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.model.dto.ReferencePriceDTO;
import com.tathao.eshopping.model.entity.ProductEntity;
import com.tathao.eshopping.model.entity.ReferencePriceEntity;

public class ReferencePriceBeanUtils {

    public static ReferencePriceEntity dto2Entity(ReferencePriceDTO dto) {
        ReferencePriceEntity entity = new ReferencePriceEntity();
        entity.setHighestPrice(dto.getHighestPrice());
        entity.setLowestPrice(dto.getLowestPrice());
        entity.setReferencePriceId(dto.getReferencePriceId());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setProduct(new ProductEntity());
        return entity;
    }

    public static ReferencePriceDTO entity2DTO(ReferencePriceEntity entity) {
        ReferencePriceDTO dto = new ReferencePriceDTO();
        dto.setHighestPrice(entity.getHighestPrice());
        dto.setLowestPrice(entity.getLowestPrice());
        dto.setReferencePriceId(entity.getReferencePriceId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setProduct(new ProductDTO());
        return dto;
    }
}
