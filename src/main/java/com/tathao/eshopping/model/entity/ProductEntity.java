package com.tathao.eshopping.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Product")
public class ProductEntity {

    private Long productId;
    private String code;
    private CatGroupEntity catGroup;
    private Long brandId;
    private String name;
    private String image;
    private String description;
    private Boolean status;
    private Integer top;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private List<ProductSkuEntity> productSkus;
    private ProductReferencePriceEntity referencePrice;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catGroupId", referencedColumnName = "catGroupId")
    public CatGroupEntity getCatGroup() {
        return catGroup;
    }

    public void setCatGroup(CatGroupEntity catGroup) {
        this.catGroup = catGroup;
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
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "product")
    public ProductReferencePriceEntity getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(ProductReferencePriceEntity referencePrice) {
        this.referencePrice = referencePrice;
    }
}
