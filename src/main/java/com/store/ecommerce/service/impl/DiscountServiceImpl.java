package com.store.ecommerce.service.impl;

import com.store.ecommerce.exception.EcommerceException;
import com.store.ecommerce.model.Discount;
import com.store.ecommerce.repository.DiscountRepo;
import com.store.ecommerce.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepo discountRepo;

    @Override
    public List<Discount> getAllDiscounts() {
        return discountRepo.findAll();
    }

    @Override
    public Optional<Discount> getDiscountById(Long discountId) {
        return discountRepo.findById(discountId);
    }

    @Override
    public Discount createDiscount(Discount discount) {
        return discountRepo.save(discount);
    }

    @Transactional
    @Override
    public Discount updateDiscount(Discount discount, Long discountId) {
        if (!discountRepo.existsById(discountId)) {
            throw new EcommerceException("discount doesn't exist");
        }

        return discountRepo.save(discount);
    }
}
