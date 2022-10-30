package com.store.ecommerce.service;

import com.store.ecommerce.model.Cart;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CartService {

    List<Cart> getAllCarts();

    Optional<Cart> getCartById(Long cartId);

    Cart createCart();

    Cart addProductToCart(Long cartId, Long productId, Long qty);

    void deleteCart(Long cartId);

    Cart removeProductFromCart(Long cartId, Long productId, Long qty);

    Cart checkout(Long cartId);
}
