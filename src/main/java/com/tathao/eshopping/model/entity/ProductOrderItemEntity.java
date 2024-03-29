package com.tathao.eshopping.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ProductOrderItem")
public class ProductOrderItemEntity {

    private Long productOrderItemId;
    private String code;
    private boolean active;
    private ProductSkuDimensionEntity skuDimensionEntity;
    private Double originalPrice;
    private Double salePrice;
    private Double discountPrice;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    @Id
    @Column(name = "ProductOrderItemId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne
    @JoinColumn(name = "ProductSkuDimensionId", referencedColumnName = "productskudimensionid")
    public ProductSkuDimensionEntity getSkuDimensionEntity() {
        return skuDimensionEntity;
    }

    public void setSkuDimensionEntity(ProductSkuDimensionEntity skuDimensionEntity) {
        this.skuDimensionEntity = skuDimensionEntity;
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
