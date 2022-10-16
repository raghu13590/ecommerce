package com.store.ecommerce.service;

import com.store.ecommerce.model.Discount;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface DiscountService {
    List<Discount> getAllDiscounts();

    Optional<Discount> getDiscountById(Long discountId);

    Discount createDiscount(Discount discount);

    Discount updateDiscount(Discount discount, Long discountId);
}
