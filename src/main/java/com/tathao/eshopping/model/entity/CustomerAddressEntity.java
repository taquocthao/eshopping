package com.tathao.eshopping.model.entity;

import com.tathao.eshopping.model.dto.CustomerAddressDTO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "CustomerAddress")
public class CustomerAddressEntity {

    private Long customerAddressId;
    private CustomerEntity customer;
    private String streetAddress;
    private String city;
    private String district;
    private String provincial;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private boolean defaultAddress;

    @Id
    @Column(name = "customerAddressId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getCustomerAddressId() {
        return customerAddressId;
    }

    public void setCustomerAddressId(Long customerAddressId) {
        this.customerAddressId = customerAddressId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerId", referencedColumnName = "CustomerId")
    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvincial() {
        return provincial;
    }

    public void setProvincial(String provincial) {
        this.provincial = provincial;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
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

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
