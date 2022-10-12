package com.store.ecommerce.repository;

import com.store.ecommerce.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepo extends JpaRepository<Discount, Long> {

    @Query(value = "SELECT * FROM Discount WHERE Discount.Discount_Id = ?1", nativeQuery = true)
    Optional<Discount> findDiscountByProductId(Long productId);
}
