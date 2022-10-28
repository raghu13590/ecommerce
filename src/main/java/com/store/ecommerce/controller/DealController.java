package com.store.ecommerce.controller;

import com.store.ecommerce.model.Deal;
import com.store.ecommerce.service.impl.IDealService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DealController {

    @Autowired
    IDealService dealService;

    @Operation(summary = "returns all deals")
    @GetMapping(value = {"/deals"})
    public List<Deal> getAllDeals() {
        return dealService.getAllDeals();
    }

    @Operation(summary = "returns a deal by it's Id.")
    @GetMapping(value = {"/deals/dealId"})
    public Optional<Deal> getDealById(@PathVariable("dealId") Long dealId) {
        return dealService.getDealById(dealId);
    }

    @Operation(summary = "creates a new deal")
    @PostMapping(value = {"/deals"})
    public Deal addDeal(@RequestBody Deal deal) {
        return dealService.createDeal(deal);
    }

    @Operation(summary = "updates an existing deal")
    @PutMapping(value = {"/deals/{dealId}"})
    public Deal updateDeal(@RequestBody Deal Deal, @PathVariable Long dealId) {
        return dealService.updateDeal(Deal, dealId);
    }
}
