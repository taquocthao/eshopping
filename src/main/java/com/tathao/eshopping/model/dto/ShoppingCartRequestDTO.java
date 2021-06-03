package com.tathao.eshopping.model.dto;

public class ShoppingCartRequestDTO {

    private String skuDimensionCode;
    private String customerCode;
    private Integer quantity;

    public String getSkuDimensionCode() {
        return skuDimensionCode;
    }

    public void setSkuDimensionCode(String skuDimensionCode) {
        this.skuDimensionCode = skuDimensionCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
