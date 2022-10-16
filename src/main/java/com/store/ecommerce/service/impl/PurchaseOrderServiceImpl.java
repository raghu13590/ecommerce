package com.store.ecommerce.service.impl;

import com.store.ecommerce.model.PurchaseOrder;
import com.store.ecommerce.repository.OrdersRepo;
import com.store.ecommerce.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private OrdersRepo ordersRepo;

    @Override
    public List<PurchaseOrder> getAllOrders() {
        return ordersRepo.findAll();
    }

    @Override
    public Optional<PurchaseOrder> getOrderById(Long purchaseOrderId) {
        return ordersRepo.findById(purchaseOrderId);
    }

    @Override
    public PurchaseOrder createOrder(PurchaseOrder order) {
        return ordersRepo.save(order);
    }
}
