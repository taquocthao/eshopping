package com.tathao.eshopping.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "ReferencePrice")
public class ReferencePriceEntity {

    private Long referencePriceId;
    private Double lowestPrice;
    private Double highestPrice;
    private ProductEntity product;

    @Id
    @Column(name = "ReferencePriceId")
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

    @OneToOne(mappedBy = "ReferencePrice")
    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
