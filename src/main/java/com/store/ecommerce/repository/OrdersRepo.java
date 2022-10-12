package com.store.ecommerce.repository;

import com.store.ecommerce.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepo extends JpaRepository<PurchaseOrder, Long> {
}
