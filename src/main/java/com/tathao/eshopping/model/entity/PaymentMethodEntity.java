package com.tathao.eshopping.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "PaymentMethod")
public class PaymentMethodEntity {

    private Long paymentMethodId;
    private String code;
    private Boolean isActive;
    private String value;
    private Integer priority;
    private Timestamp createDate;
    private Timestamp modifiedDate;

    @Id
    @Column(name = "PaymentMethodId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
