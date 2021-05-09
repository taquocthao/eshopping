package com.tathao.eshopping.model.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class ProductDTO implements Serializable {

    private Long productId;
    private String code;
    private CatGroupDTO catGroup;
    private Long brandId;
    private String name;
    private String image;
    private String description;
    private boolean status;
    private Integer top;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private List<ProductSkuDTO> sku;
    private ProductReferencePriceDTO referencePrice;
    private List<ProductDTO> relatedProducts;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CatGroupDTO getCatGroup() {
        return catGroup;
    }

    public void setCatGroup(CatGroupDTO catGroup) {
        this.catGroup = catGroup;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
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

    public List<ProductSkuDTO> getSku() {
        return sku;
    }

    public void setSku(List<ProductSkuDTO> sku) {
        this.sku = sku;
    }

    public ProductReferencePriceDTO getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(ProductReferencePriceDTO referencePrice) {
        this.referencePrice = referencePrice;
    }

    public List<ProductDTO> getRelatedProducts() {
        return relatedProducts;
    }

    public void setRelatedProducts(List<ProductDTO> relatedProducts) {
        this.relatedProducts = relatedProducts;
    }
}
