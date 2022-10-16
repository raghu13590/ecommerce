package com.store.ecommerce.disocuntutil;


public class OffCalculator implements DiscountCalculator {

    /***
     * flat percentage off on a product
     */
    @Override
    public Double getDiscount(Integer percentageOff, Double price, Long qty) {

        return price*qty*(percentageOff/100);
    }
}