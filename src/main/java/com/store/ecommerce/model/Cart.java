package com.store.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "CART")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "cartId")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long cartId;
    @Column(nullable = false)
    private LocalDate createTime;
    @Column
    private LocalDate lastUpdate;
    @OneToMany(mappedBy = "cart")
    private Set<CartItem> itemsInCart = new HashSet<>();
    @Column
    private Double total;
    @Version
    private Long version;

    @ManyToMany
    @JoinTable(name = "cart_deal",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "deal_id"))
    private Set<Deal> deal = new LinkedHashSet<>();

    public Set<Deal> getDeal() {
        return deal;
    }

    public void setDeal(Set<Deal> deal) {
        this.deal = deal;
    }

    public Cart() {
        this.createTime = LocalDate.now();
        this.total = 0.00;
    }

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
