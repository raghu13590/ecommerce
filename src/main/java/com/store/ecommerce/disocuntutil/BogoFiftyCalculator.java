package com.store.ecommerce.disocuntutil;

public class BogoFiftyCalculator implements DiscountCalculator {

    /***
     * buy one get 50% off on another one
     */
    @Override
    public Double getDiscount(Integer percentageOff, Double price, Long qty) {
        // if even
        if (qty % 2 == 0) {
            return price * (qty / 2) + price * (0.5) * (qty / 2);
        } else {
            return price * ((qty / 2) + 1) + price * (0.5) * (qty / 2);
        }
    }
}
