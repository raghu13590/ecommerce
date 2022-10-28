package com.store.ecommerce.service.impl;

import com.store.ecommerce.exception.EcommerceException;
import com.store.ecommerce.model.Deal;
import com.store.ecommerce.model.Product;
import com.store.ecommerce.model.ProductDeal;
import com.store.ecommerce.repository.DealRepo;
import com.store.ecommerce.repository.ProductDealRepo;
import com.store.ecommerce.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DealServiceImpl implements IDealService{

    @Autowired
    DealRepo dealRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    ProductDealRepo productDealRepo;

    @Override
    public List<Deal> getAllDeals() {
        return dealRepo.findAll();
    }

    @Override
    public Optional<Deal> getDealById(Long dealId) {
        return dealRepo.findById(dealId);
    }

    @Override
    public Deal createDeal(Deal deal) {
        validateDeal(deal);
        return dealRepo.save(deal);
    }

    @Override
    public Deal updateDeal(Deal deal, Long dealId) {
        if (!dealRepo.existsById(dealId)) {
            throw new EcommerceException("deal doesn't exist");
        }

        validateDeal(deal);
        deal.setDealId(dealId);
        deal.getProductDeals().forEach(productDeal -> {
            productDeal.setProduct(productRepo.findById(productDeal.getProduct().getProductId()).get());
            productDeal.setDeal(deal);
            productDealRepo.save(productDeal);
        });

        return dealRepo.save(deal);
    }

    private void validateDeal(Deal deal) {
        deal.getProductDeals().forEach(productDeal -> {
            if (productDeal.getProduct().getProductId() == null || productRepo.findById(productDeal.getProduct().getProductId()).isEmpty()) {
                throw new EcommerceException("product doesn't exist, productId :: " +productDeal.getProduct().getProductId());
            }

            if (productDeal.getProductDealType() == null) {
                throw new EcommerceException("productDealType required for product :: " +productDeal.getProduct().getProductId());
            }
        });
    }
}
