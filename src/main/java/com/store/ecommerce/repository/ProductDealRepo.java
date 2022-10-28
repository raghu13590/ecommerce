package com.store.ecommerce.repository;

import com.store.ecommerce.model.Deal;
import com.store.ecommerce.model.ProductDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDealRepo extends JpaRepository<ProductDeal, Long> {

}
