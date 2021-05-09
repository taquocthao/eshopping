package com.tathao.eshopping.model.entity;

import com.tathao.eshopping.model.dto.ProductDTO;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ProductSku")
public class ProductSkuEntity {

    private Long productSkuId;
    private ProductEntity product;
    private String skuCode;
    private String title;
    private String image;
    private Double originalPrice;
    private Double salePrice;
    private String unit;
    private String barCode;
    private Boolean status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String description;

    public ProductSkuEntity() {
    }
    public ProductSkuEntity(Long productSkuId) {
        this.productSkuId = productSkuId;
    }


    @Id
    @Column(name = "ProductSkuId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    @ManyToOne
    @JoinColumn(name = "ProductId", referencedColumnName = "ProductId")
    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
