package com.tathao.eshopping.ultils;

import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.model.entity.ProductEntity;

public class ProductBeanUtils {

    public static ProductEntity dto2Entity(ProductDTO dto) {
        ProductEntity entity = new ProductEntity();
        entity.setBrandId(dto.getBrandId());
        entity.setCatgroupId(dto.getCatgroupId());
        entity.setCode(dto.getCode());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setImage(dto.getImage());
        entity.setName(dto.getName());
        entity.setStatus(dto.isStatus());
        entity.setDescription(dto.getDescription());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setProductId(dto.getProductId());
        entity.setTop(dto.getTop());
        if(dto.getReferencePrice() != null) {
            entity.setReferencePrice(ReferencePriceBeanUtils.dto2Entity(dto.getReferencePrice()));
        }
        return entity;
    }

    public static ProductDTO entity2DTO(ProductEntity entity) {
        ProductDTO dto = new ProductDTO();
        dto.setBrandId(entity.getBrandId());
        dto.setCatgroupId(entity.getCatgroupId());
        dto.setCode(entity.getCode());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setImage(entity.getImage());
        dto.setName(entity.getName());
        dto.setStatus(entity.isStatus());
        dto.setDescription(entity.getDescription());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setProductId(entity.getProductId());
        dto.setTop(entity.getTop());
        if(entity.getReferencePrice() != null) {
            dto.setReferencePrice(ReferencePriceBeanUtils.entity2DTO(entity.getReferencePrice()));
        }
        return dto;
    }

}
