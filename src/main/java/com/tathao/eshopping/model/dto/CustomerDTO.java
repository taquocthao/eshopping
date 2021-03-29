package com.tathao.eshopping.model.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class CustomerDTO implements Serializable {

    private Long customerId;
    private UserDTO user;
    private boolean active;
    private Timestamp birthday;
    private Timestamp createdDate;
    private Timestamp lastLogin;
    private Timestamp beforeLastLogin;
    private List<CustomerAddressDTO> customerAddress;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
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

    public List<CustomerAddressDTO> getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(List<CustomerAddressDTO> customerAddress) {
        this.customerAddress = customerAddress;
    }
}
