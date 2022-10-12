package com.store.ecommerce.service;

import com.store.ecommerce.model.PurchaseOrder;
import com.store.ecommerce.repository.OrdersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {

    @Autowired
    private OrdersRepo ordersRepo;

    public List<PurchaseOrder> getAllOrders() {
        return ordersRepo.findAll();
    }

    public Optional<PurchaseOrder> getOrderById(Long purchaseOrderId) {
        return ordersRepo.findById(purchaseOrderId);
    }

    public PurchaseOrder createOrder(PurchaseOrder order) {
        return ordersRepo.save(order);
    }
}
