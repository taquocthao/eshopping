package com.tathao.eshopping.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ProductSkuDimension")
public class ProductSkuDimensionEntity {

    private Long productSkuDimensionId;
    private ProductSkuEntity sku;
    private String code;
    private String size;
    private Integer width;
    private Integer height;
    private Integer depth;
    private Double originalPrice;
    private Double salePrice;
    private String barCode;
    private Boolean active;
    private Timestamp createdDate;
    private Timestamp modifiedDate;


    @Id
    @Column(name = "ProductSkuDimensionId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getProductSkuDimensionId() {
        return productSkuDimensionId;
    }

    public void setProductSkuDimensionId(Long productSkuDimensionId) {
        this.productSkuDimensionId = productSkuDimensionId;
    }

    @ManyToOne
    @JoinColumn(name = "ProductSkuId", referencedColumnName = "ProductSkuId")
    public ProductSkuEntity getSku() {
        return sku;
    }

    public void setSku(ProductSkuEntity sku) {
        this.sku = sku;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
