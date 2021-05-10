package com.tathao.eshopping.ultils.mapper.handle;

import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.model.dto.ProductSkuDTO;
import com.tathao.eshopping.model.entity.ProductEntity;
import com.tathao.eshopping.model.entity.ProductSkuEntity;

import java.util.ArrayList;
import java.util.List;

public class ProductBeanUtils {

    public static ProductEntity dto2Entity(ProductDTO dto) {
        ProductEntity entity = new ProductEntity();
        entity.setBrandId(dto.getBrandId());
        if(dto.getCatGroup() != null) {
            entity.setCatGroup(CatGroupBeanUtils.dto2Entity(dto.getCatGroup()));
        }
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
            entity.setReferencePrice(ProductReferencePriceBeanUtils.dto2Entity(dto.getReferencePrice()));
        }
        List<ProductSkuEntity> skuEntities = new ArrayList<>();
        for(ProductSkuDTO skuDTO : dto.getSku()) {
            skuEntities.add(ProductSkuBeanUtils.dto2Entity(skuDTO));
        }
        entity.setProductSkus(skuEntities);
        return entity;
    }

    public static ProductDTO entity2DTO(ProductEntity entity) {
        ProductDTO dto = new ProductDTO();
        dto.setBrandId(entity.getBrandId());
        dto.setCatGroup(CatGroupBeanUtils.entity2DTO(entity.getCatGroup()));
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
            dto.setReferencePrice(ProductReferencePriceBeanUtils.entity2DTO(entity.getReferencePrice()));
        }
        List<ProductSkuDTO> skuDTOS = new ArrayList<>();
        for(ProductSkuEntity skuEntity : entity.getProductSkus()){
            skuDTOS.add(ProductSkuBeanUtils.entity2DTO(skuEntity));
        }
        dto.setSku(skuDTOS);
        return dto;
    }

    public static ProductDTO entity2DTOAndFetchRelatedProducts(ProductEntity entity, List<ProductEntity> relatedProducts) {
        ProductDTO dto = entity2DTO(entity);
        List<ProductDTO> relatedProductsDTO = new ArrayList<>();
        for(ProductEntity relatedProduct : relatedProducts) {
            relatedProductsDTO.add(entity2DTO(relatedProduct));
        }
        dto.setRelatedProducts(relatedProductsDTO);
        return dto;
    }
}
