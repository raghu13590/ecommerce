package com.store.ecommerce.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long dealId;

    @Column
    private LocalDate startTime;

    @Column
    private LocalDate endTime;

    @Column(nullable = false)
    private DealType dealType;

    @OneToMany(mappedBy = "productDealId")
    private Set<ProductDeal> productDeals = new HashSet<>();

    @Column(nullable = false)
    private Boolean active;

    @Column
    private Boolean combinable;

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

    public DealType getDealType() {
        return dealType;
    }

    public void setDealType(DealType dealType) {
        this.dealType = dealType;
    }

    public Set<ProductDeal> getProductDeals() {
        return productDeals;
    }

    public void setProductDeals(Set<ProductDeal> productDeals) {
        this.productDeals = productDeals;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getCombinable() {
        return combinable;
    }

    public void setCombinable(Boolean combinable) {
        this.combinable = combinable;
    }

    public enum DealType {
        BUY_TWO_GET_ONE, BUY_THREE_GET_ONE
    }
}