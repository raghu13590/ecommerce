package com.store.ecommerce.service;

import com.store.ecommerce.model.PurchaseOrder;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderService {
    List<PurchaseOrder> getAllOrders();

    Optional<PurchaseOrder> getOrderById(Long purchaseOrderId);

    PurchaseOrder createOrder(PurchaseOrder order);
}
