package com.tathao.eshopping.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ShoppingCart")
public class ShoppingCartEntity {

    private Long shoppingCartId;
    private ProductSkuDimensionEntity skuDimension;
    private Integer quantity;
    private CustomerEntity customer;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private UserEntity referenceBy;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    @ManyToOne
    @JoinColumn(name = "skuDimensionId", referencedColumnName = "ProductSkuDimensionId")
    public ProductSkuDimensionEntity getSkuDimension() {
        return skuDimension;
    }

    public void setSkuDimension(ProductSkuDimensionEntity skuDimension) {
        this.skuDimension = skuDimension;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", referencedColumnName = "customerId")
    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referenceBy", referencedColumnName = "userId")
    public UserEntity getReferenceBy() {
        return referenceBy;
    }

    public void setReferenceBy(UserEntity referenceBy) {
        this.referenceBy = referenceBy;
    }
}
