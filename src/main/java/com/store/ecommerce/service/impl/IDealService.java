package com.store.ecommerce.service.impl;

import com.store.ecommerce.model.Deal;

import java.util.List;
import java.util.Optional;

public interface IDealService {

    List<Deal> getAllDeals();

    Optional<Deal> getDealById(Long dealId);

    Deal createDeal(Deal deal);

    Deal updateDeal(Deal deal, Long dealId);
}
