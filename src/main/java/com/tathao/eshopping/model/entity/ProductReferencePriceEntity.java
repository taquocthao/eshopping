package com.tathao.eshopping.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ProductReferencePrice")
public class ProductReferencePriceEntity {

    private Long referencePriceId;
    private Double lowestPrice;
    private Double highestPrice;
    private ProductEntity product;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    @Id
    @Column(name = "ProductReferencePriceId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne
    @JoinColumn(name = "productid", referencedColumnName = "productId")
    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
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
