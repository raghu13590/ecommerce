package com.store.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRODUCT", uniqueConstraints = {@UniqueConstraint(name = "product_name_unique", columnNames = "name")})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long productId;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Long quantity;
    @Column
    private String pictureUrl;
    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<CartItem> inCart = new HashSet<>();
    @Column(nullable = false)
    private Boolean active;
    @Version
    private Long version;

    public Product() {
    }

    public Product(Double price, Long quantity, Boolean active) {
        this.price = price;
        this.quantity = quantity;
        this.active = active;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Set<CartItem> getInCart() {
        return inCart;
    }

    public void setInCart(Set<CartItem> inCart) {
        this.inCart = inCart;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
