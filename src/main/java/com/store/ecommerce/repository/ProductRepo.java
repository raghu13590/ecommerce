package com.store.ecommerce.repository;

import com.store.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {


    @Query(value = "SELECT * FROM Product WHERE Product.name = ?1", nativeQuery = true)
    Optional<Product> getProductByName(String name);

    @Query(value = "SELECT Quantity FROM Product WHERE Product.productId = ?1", nativeQuery = true)
    Long getQtyByProductId(Long productId);
}
