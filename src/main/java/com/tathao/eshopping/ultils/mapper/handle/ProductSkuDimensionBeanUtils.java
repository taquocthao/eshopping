package com.tathao.eshopping.ultils.mapper.handle;

import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.model.dto.ProductSkuDTO;
import com.tathao.eshopping.model.dto.ProductSkuDimensionDTO;
import com.tathao.eshopping.model.entity.ProductSkuDimensionEntity;
import com.tathao.eshopping.model.entity.ProductSkuEntity;

public class ProductSkuDimensionBeanUtils {

    public static ProductSkuDimensionDTO entity2DTO(ProductSkuDimensionEntity entity) {
        if(entity == null) {
            return null;
        }
        ProductSkuDimensionDTO dto = new ProductSkuDimensionDTO();
        dto.setActive(entity.getActive());
        dto.setWidth(entity.getWidth());
        if(entity.getSku() != null) {
            ProductSkuDTO skuDTO = new ProductSkuDTO();
            skuDTO.setProductSkuId(entity.getSku().getProductSkuId());
            skuDTO.setImage(entity.getSku().getImage());
            skuDTO.setTitle(entity.getSku().getTitle());
            ProductDTO productDTO = new ProductDTO();
            productDTO.setName(entity.getSku().getProduct().getName());
            productDTO.setCatGroup(CatGroupBeanUtils.entity2DTO(entity.getSku().getProduct().getCatGroup()));
            skuDTO.setProduct(productDTO);
            dto.setSku(skuDTO);
        }
        dto.setSize(entity.getSize());
        dto.setSalePrice(entity.getSalePrice());
        dto.setProductSkuDimensionId(entity.getProductSkuDimensionId());
        dto.setOriginalPrice(entity.getOriginalPrice());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setHeight(entity.getHeight());
        dto.setDepth(entity.getDepth());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCode(entity.getCode());
        dto.setBarCode(entity.getBarCode());
        return dto;
    }

    public static ProductSkuDimensionEntity dto2Entity(ProductSkuDimensionDTO dto) {
        if(dto == null) {
            return null;
        }
        ProductSkuDimensionEntity entity = new ProductSkuDimensionEntity();
        entity.setActive(dto.getActive());
        entity.setWidth(dto.getWidth());
        if(dto.getSku() != null) {
            ProductSkuEntity skuEntity = new ProductSkuEntity();
            skuEntity.setProductSkuId(dto.getSku().getProductSkuId());
            entity.setSku(skuEntity);
        }
        entity.setSize(dto.getSize());
        entity.setSalePrice(dto.getSalePrice());
        entity.setProductSkuDimensionId(dto.getProductSkuDimensionId());
        entity.setOriginalPrice(dto.getOriginalPrice());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setHeight(dto.getHeight());
        entity.setDepth(dto.getDepth());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setCode(dto.getCode());
        entity.setBarCode(dto.getBarCode());
        return entity;
    }

}
