package com.store.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Cart {

    public Cart() {
        this.createTime = LocalDate.now();
        this.total = 0.00;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long cartId;

    @Column(nullable = false)
    private LocalDate createTime;

    @Column
    private LocalDate lastUpdate;

    @JsonIgnore
    @OneToMany(mappedBy = "cart")
    private Set<CartItem> itemsInCart = new HashSet<>();

    @Column
    private Double total;

    @Version
    private Long version;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Set<CartItem> getItemsInCart() {
        return itemsInCart;
    }

    public void setItemsInCart(Set<CartItem> itemsInCart) {
        this.itemsInCart = itemsInCart;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
