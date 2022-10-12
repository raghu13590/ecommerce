package com.store.ecommerce.controller;

import com.store.ecommerce.model.Discount;
import com.store.ecommerce.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DiscountController {

    @Autowired
    DiscountService discountService;

    @GetMapping(value = {"/discounts"})
    public List<Discount> getAllDiscounts() {
        return discountService.getAllDiscounts();
    }

    @GetMapping(value = {"/discount/discountId"})
    public Optional<Discount> getDiscountById(@PathVariable("discountId") Long discountId) {
        return discountService.getDiscountById(discountId);
    }

    @PostMapping(value = {"/discount"})
    public Discount addDiscount(@RequestBody Discount discount) {
        return discountService.createDiscount(discount);
    }

    @PutMapping(value = {"/discount/{discountId}"})
    public Discount updateDiscount(@RequestBody Discount discount, @PathVariable Long discountId) {
        return discountService.updateDiscount(discount, discountId);
    }
}