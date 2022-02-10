package com.tathao.eshopping.model.dto;


import java.io.Serializable;
import java.sql.Timestamp;

public class ProductOrderItemDTO implements Serializable {

    private Long productOrderItemId;
    private String code;
    private boolean active;
    private ProductSkuDimensionDTO skuDimensionDTO;
    private Double originalPrice;
    private Double salePrice;
    private Double discountPrice;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    public Long getProductOrderItemId() {
        return productOrderItemId;
    }

    public void setProductOrderItemId(Long productOrderItemId) {
        this.productOrderItemId = productOrderItemId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ProductSkuDimensionDTO getSkuDimensionDTO() {
        return skuDimensionDTO;
    }

    public void setSkuDimensionDTO(ProductSkuDimensionDTO skuDimensionDTO) {
        this.skuDimensionDTO = skuDimensionDTO;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
