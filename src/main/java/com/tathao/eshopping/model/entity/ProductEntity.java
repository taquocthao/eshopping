package com.tathao.eshopping.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Product")
public class ProductEntity {

    private Long productId;
    private String code;
    private Long catgroupId;
    private Long brandId;
    private String name;
    private String image;
    private String description;
    private boolean status;
    private Integer top;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private List<ProductSkuEntity> productSkus;

    @Id
    @Column(name = "ProductId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "Code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "CatGroupId")
    public Long getCatgroupId() {
        return catgroupId;
    }

    public void setCatgroupId(Long catgroupId) {
        this.catgroupId = catgroupId;
    }

    @Column(name = "BrandId")
    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "Image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "Status")
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Column(name = "Top")
    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    @Column(name = "CreatedDate")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "ModifiedDate")
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    public List<ProductSkuEntity> getProductSkus() {
        return productSkus;
    }

    public void setProductSkus(List<ProductSkuEntity> productSkus) {
        this.productSkus = productSkus;
    }
}
