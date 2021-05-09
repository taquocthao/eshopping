package com.tathao.eshopping.ultils;

import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.model.dto.ProductSkuDTO;
import com.tathao.eshopping.model.entity.ProductSkuEntity;

public class ProductSkuBeanUtils {

    public static ProductSkuEntity dto2Entity(ProductSkuDTO dto) {
        ProductSkuEntity entity = new ProductSkuEntity();
        entity.setBarCode(dto.getBarCode());
        entity.setImage(dto.getImage());
        entity.setOriginalPrice(dto.getOriginalPrice());
        entity.setProduct(ProductBeanUtils.dto2Entity(dto.getProduct()));
        entity.setProductSkuId(dto.getProductSkuId());
        entity.setSalePrice(dto.getSalePrice());
        entity.setSkuCode(dto.getSkuCode());
        entity.setStatus(dto.getStatus());
        entity.setTitle(dto.getTitle());
        entity.setUnit(dto.getUnit());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public static ProductSkuDTO entity2DTO(ProductSkuEntity entity) {
        ProductSkuDTO dto = new ProductSkuDTO();
        dto.setBarCode(entity.getBarCode());
        dto.setImage(entity.getImage());
        dto.setOriginalPrice(entity.getOriginalPrice());
        if(entity.getProduct() != null) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(entity.getProduct().getProductId());
            dto.setProduct(productDTO);
        }
        dto.setProductSkuId(entity.getProductSkuId());
        dto.setSalePrice(entity.getSalePrice());
        dto.setSkuCode(entity.getSkuCode());
        dto.setStatus(entity.getStatus());
        dto.setTitle(entity.getTitle());
        dto.setUnit(entity.getUnit());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
