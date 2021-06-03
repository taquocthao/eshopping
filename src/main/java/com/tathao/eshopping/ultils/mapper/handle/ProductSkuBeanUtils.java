package com.tathao.eshopping.ultils.mapper.handle;

import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.model.dto.ProductSkuDTO;
import com.tathao.eshopping.model.entity.ProductSkuEntity;

import java.util.stream.Collectors;

public class ProductSkuBeanUtils {


    public static ProductSkuEntity dto2Entity(ProductSkuDTO dto) {
        ProductSkuEntity entity = new ProductSkuEntity();
        entity.setImage(dto.getImage());
        entity.setProduct(ProductBeanUtils.dto2Entity(dto.getProduct()));
        entity.setProductSkuId(dto.getProductSkuId());
        entity.setSkuCode(dto.getSkuCode());
        entity.setStatus(dto.getStatus());
        entity.setTitle(dto.getTitle());
        entity.setUnit(dto.getUnit());
        entity.setDescription(dto.getDescription());
        entity.setSkuDimensions(dto.getSkuDimensionDTOs()
                .stream()
                .map(ProductSkuDimensionBeanUtils::dto2Entity)
                .collect(Collectors.toList()));
        return entity;
    }

    public static ProductSkuDTO entity2DTO(ProductSkuEntity entity) {
        ProductSkuDTO dto = new ProductSkuDTO();
        dto.setImage(entity.getImage());
        if(entity.getProduct() != null) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(entity.getProduct().getProductId());
            dto.setProduct(productDTO);
        }
        dto.setProductSkuId(entity.getProductSkuId());
        dto.setSkuCode(entity.getSkuCode());
        dto.setStatus(entity.getStatus());
        dto.setTitle(entity.getTitle());
        dto.setUnit(entity.getUnit());
        dto.setDescription(entity.getDescription());

        dto.setSkuDimensionDTOs(entity.getSkuDimensions()
                .stream()
                .map(ProductSkuDimensionBeanUtils::entity2DTO)
                .collect(Collectors.toList()));

        return dto;
    }
}
