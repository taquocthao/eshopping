package com.tathao.eshopping.model.entity;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Customer")
public class CustomerEntity {

    private Long customerId;
    private String code;
    private UserEntity user;
    private boolean active;
    private Timestamp birthday;
    private Timestamp createdDate;
    private Timestamp lastLogin;
    private Timestamp beforeLastLogin;
    private List<CustomerAddressEntity> customerAddress;

    public CustomerEntity() {
    }

    public CustomerEntity(Long customerId) {
        this.customerId = customerId;
    }

    @Id
    @Column(name = "customerId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Column(name = "Code", nullable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Timestamp getBeforeLastLogin() {
        return beforeLastLogin;
    }

    public void setBeforeLastLogin(Timestamp beforeLastLogin) {
        this.beforeLastLogin = beforeLastLogin;
    }

    @OneToMany(mappedBy = "customer")
    public List<CustomerAddressEntity> getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(List<CustomerAddressEntity> customerAddress) {
        this.customerAddress = customerAddress;
    }
}
