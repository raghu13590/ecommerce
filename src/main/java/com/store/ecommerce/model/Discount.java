package com.store.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long discountId;
    @Column(nullable = false)
    private DiscountType discountType;
    @Column(nullable = false)
    private Integer percentageOff;
    @JsonIgnore
    @OneToMany(mappedBy = "discount")
    private Set<Product> products = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "discount")
    private Set<CartItem> discountedItemsInCart = new HashSet<>();
    @Version
    private Long version;

    public Discount() {
    }

    public Discount(DiscountType discountType, Integer percentageOff) {
        this.discountType = discountType;
        this.percentageOff = percentageOff;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public Integer getPercentageOff() {
        return percentageOff;
    }

    public void setPercentageOff(Integer percentageOff) {
        this.percentageOff = percentageOff;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<CartItem> getDiscountedItemsInCart() {
        return discountedItemsInCart;
    }

    public void setDiscountedItemsInCart(Set<CartItem> discountedItemsInCart) {
        this.discountedItemsInCart = discountedItemsInCart;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public enum DiscountType {
        OFF, SALE
    }
}