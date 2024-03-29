package com.tathao.eshopping.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "orderoutlet")
public class OrderOutletEntity {

    private Long orderOutletId;
    private String code;
    private String status;
    private CustomerEntity customer;
    private UserEntity createdBy;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private Double totalPrice; // totalPrice = totalOriginalPrice - totalDiscountPrice
    private Double totalOriginalPrice;
    private Double totalDiscountPrice; // totalDiscountPrice = totalStoreDiscount + totalLoyaltyDiscount + totalPromotionDiscount
    private Double totalStoreDiscount;
    private Double totalLoyaltyDiscount;
    private Double totalPromotionDiscount;
    private List<ProductOrderItemEntity> orderItems;

    @Id
    @Column(name = "OrderOutletId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getOrderOutletId() {
        return orderOutletId;
    }

    public void setOrderOutletId(Long orderOutletId) {
        this.orderOutletId = orderOutletId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ManyToOne
    @JoinColumn(name = "CustomerId", referencedColumnName = "customerid")
    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    @ManyToOne
    @JoinColumn(name = "createdBy", referencedColumnName = "userid")
    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalOriginalPrice() {
        return totalOriginalPrice;
    }

    public void setTotalOriginalPrice(Double totalOriginalPrice) {
        this.totalOriginalPrice = totalOriginalPrice;
    }

    public Double getTotalDiscountPrice() {
        return totalDiscountPrice;
    }

    public void setTotalDiscountPrice(Double totalDiscountPrice) {
        this.totalDiscountPrice = totalDiscountPrice;
    }

    public Double getTotalStoreDiscount() {
        return totalStoreDiscount;
    }

    public void setTotalStoreDiscount(Double totalStoreDiscount) {
        this.totalStoreDiscount = totalStoreDiscount;
    }

    public Double getTotalLoyaltyDiscount() {
        return totalLoyaltyDiscount;
    }

    public void setTotalLoyaltyDiscount(Double totalLoyaltyDiscount) {
        this.totalLoyaltyDiscount = totalLoyaltyDiscount;
    }

    public Double getTotalPromotionDiscount() {
        return totalPromotionDiscount;
    }

    public void setTotalPromotionDiscount(Double totalPromotionDiscount) {
        this.totalPromotionDiscount = totalPromotionDiscount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToMany
    @JoinColumn(name = "ProductOrderItemId", referencedColumnName = "orderoutletid")
    public List<ProductOrderItemEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ProductOrderItemEntity> orderItems) {
        this.orderItems = orderItems;
    }
}
