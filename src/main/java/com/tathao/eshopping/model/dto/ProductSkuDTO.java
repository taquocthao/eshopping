package com.tathao.eshopping.model.dto;

import java.io.Serializable;
import java.util.List;

public class ProductSkuDTO implements Serializable {
    private Long productSkuId;
    private ProductDTO product;
    private String skuCode;
    private String title;
    private String image;
    private String unit;
    private Boolean status;
    private String description;
    private List<ProductSkuDimensionDTO> skuDimensionDTOs;

    public ProductSkuDTO(){}

    public ProductSkuDTO(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductSkuDimensionDTO> getSkuDimensionDTOs() {
        return skuDimensionDTOs;
    }

    public void setSkuDimensionDTOs(List<ProductSkuDimensionDTO> skuDimensionDTOs) {
        this.skuDimensionDTOs = skuDimensionDTOs;
    }
}
