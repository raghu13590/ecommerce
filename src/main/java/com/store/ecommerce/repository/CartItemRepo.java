package com.store.ecommerce.repository;

import com.store.ecommerce.model.CartItem;
import com.store.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

    @Query(value = "SELECT * FROM Cart_Item WHERE Cart_Item.cart_Id = ?1 and Cart_Item.product_Id = ?2", nativeQuery = true)
    Optional<CartItem> getCartItemByCartIdAndProductId(Long cartId, Long productId);
}
