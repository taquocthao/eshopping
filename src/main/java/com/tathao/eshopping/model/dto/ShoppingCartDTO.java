package com.tathao.eshopping.model.dto;

import java.sql.Timestamp;

public class ShoppingCartDTO {

    private Long shoppingCartId;
    private ProductSkuDTO sku;
    private Integer quantity;
    private CustomerDTO customer;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private UserDTO referenceBy;

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public ProductSkuDTO getSku() {
        return sku;
    }

    public void setSku(ProductSkuDTO sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public UserDTO getReferenceBy() {
        return referenceBy;
    }

    public void setReferenceBy(UserDTO referenceBy) {
        this.referenceBy = referenceBy;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
