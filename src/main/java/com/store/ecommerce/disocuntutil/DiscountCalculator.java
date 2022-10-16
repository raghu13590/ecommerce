package com.store.ecommerce.disocuntutil;

import org.springframework.stereotype.Component;

@Component
public interface DiscountCalculator {

    Double getDiscount(Integer percentageOff, Double price, Long qty);
}
