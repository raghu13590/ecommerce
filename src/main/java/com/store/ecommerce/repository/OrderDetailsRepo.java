package com.store.ecommerce.repository;

import com.store.ecommerce.model.PurchaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepo extends JpaRepository<PurchaseOrderDetail, Long> {
}
