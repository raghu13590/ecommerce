package com.store.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT_DEAL")
public class ProductDeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long productDealId;

    @Column(nullable = false)
    private ProductDealType productDealType;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "deal_id", nullable = false)
    private Deal deal;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }


    public Long getProductDealId() {
        return productDealId;
    }

    public void setProductDealId(Long productDealId) {
        this.productDealId = productDealId;
    }

    public ProductDealType getProductDealType() {
        return productDealType;
    }

    public void setProductDealType(ProductDealType productDealType) {
        this.productDealType = productDealType;
    }

    public enum ProductDealType {
        ELIGIBLE_ITEM, FREE_ITEM
    }
}
