package com.store.ecommerce.controller;

import com.store.ecommerce.model.PurchaseOrder;
import com.store.ecommerce.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @GetMapping(value = {"/purchaseOrders"})
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderService.getAllOrders();
    }

    @GetMapping(value = {"/purchaseOrder/purchaseOrderId"})
    public Optional<PurchaseOrder> getPurchaseOrderById(@PathVariable("purchaseOrderId") Long purchaseOrderId) {
        return purchaseOrderService.getOrderById(purchaseOrderId);
    }

    @PostMapping(value = {"/purchaseOrder"})
    public PurchaseOrder addPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        return purchaseOrderService.createOrder(purchaseOrder);
    }
}
