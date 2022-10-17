package com.store.ecommerce.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PURCHASE_ORDER")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long purchaseOrdersId;
    @Column
    private LocalDate createTime;
    @Column
    private Double total;
    @OneToMany(mappedBy = "purchaseOrder")
    private Set<PurchaseOrderDetail> purchaseOrderDetails = new HashSet<>();
    @Version
    private Long version;

    PurchaseOrder() {
        this.createTime = LocalDate.now();
        this.total = 0.00;
    }

    public Long getPurchaseOrdersId() {
        return purchaseOrdersId;
    }

    public void setPurchaseOrdersId(Long purchaseOrdersId) {
        this.purchaseOrdersId = purchaseOrdersId;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Set<PurchaseOrderDetail> getPurchaseOrderDetails() {
        return purchaseOrderDetails;
    }

    public void setPurchaseOrderDetails(Set<PurchaseOrderDetail> purchaseOrderDetails) {
        this.purchaseOrderDetails = purchaseOrderDetails;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
