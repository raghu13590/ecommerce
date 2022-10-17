package com.store.ecommerce.controller;

import com.store.ecommerce.model.Discount;
import com.store.ecommerce.service.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DiscountController {

    @Autowired
    DiscountService discountService;

    @Operation(summary = "returns all discounts")
    @GetMapping(value = {"/discounts"})
    public List<Discount> getAllDiscounts() {
        return discountService.getAllDiscounts();
    }

    @Operation(summary = "returns a discount by it's Id.")
    @GetMapping(value = {"/discounts/discountId"})
    public Optional<Discount> getDiscountById(@PathVariable("discountId") Long discountId) {
        return discountService.getDiscountById(discountId);
    }

    @Operation(summary = "creates a new discount")
    @PostMapping(value = {"/discounts"})
    public Discount addDiscount(@RequestBody Discount discount) {
        return discountService.createDiscount(discount);
    }

    @Operation(summary = "updates an existing discount")
    @PutMapping(value = {"/discounts/{discountId}"})
    public Discount updateDiscount(@RequestBody Discount discount, @PathVariable Long discountId) {
        return discountService.updateDiscount(discount, discountId);
    }
}