package com.store.ecommerce.repository;

import com.store.ecommerce.model.Deal;
import com.store.ecommerce.model.ProductDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductDealRepo extends JpaRepository<ProductDeal, Long> {

    @Query(value = "SELECT * FROM Product_Deal WHERE Product_Deal.Product_Id = ?1", nativeQuery = true)
    List<ProductDeal> findByProductId(Long productId);
}
