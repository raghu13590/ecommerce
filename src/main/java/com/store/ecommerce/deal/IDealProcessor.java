package com.store.ecommerce.deal;

import com.store.ecommerce.model.Cart;
import com.store.ecommerce.model.Deal;

import java.util.HashSet;
import java.util.Set;

public interface IDealProcessor {

    Set<Deal> getDeals(Cart cart);

    Cart applyDeals(Cart cart, Set<Deal> deals);
}
