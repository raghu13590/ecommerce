package com.store.ecommerce.disocuntutil;

import com.store.ecommerce.model.Discount;
import org.springframework.stereotype.Component;

@Component
public class DiscountFactory {

    public DiscountCalculator getDiscountCalculator(Discount.DiscountType discountType) {
        if (discountType == Discount.DiscountType.OFF) {
            return new OffCalculator();
        } else if (discountType == Discount.DiscountType.BOGO50) {
            return new BogoFiftyCalculator();
        }

        return null;
    }
}
