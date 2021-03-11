package com.tathao.eshopping.model.dto;


import java.sql.Timestamp;

public class ReferencePriceDTO {

    private Long referencePriceId;
    private Double lowestPrice;
    private Double highestPrice;
    private ProductDTO product;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    public Long getReferencePriceId() {
        return referencePriceId;
    }

    public void setReferencePriceId(Long referencePriceId) {
        this.referencePriceId = referencePriceId;
    }

    public Double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public Double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Double highestPrice) {
        this.highestPrice = highestPrice;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
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
}
