package com.store.ecommerce.controller;

import com.store.ecommerce.model.Cart;
import com.store.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping(value = {"/carts"})
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping(value = {"/cart/{cartId}"})
    public Optional<Cart> getCart(@PathVariable Long cartId) {
        return cartService.getCart(cartId);
    }

    @PostMapping(value = {"/cart"})
    public Cart createCart() {
        return cartService.createCart();
    }

    @PutMapping(value = {"/cart/{cartId}/add/{productId}/{qty}"})
    public Cart addProductToCart(@PathVariable Long cartId, @PathVariable Long productId, @PathVariable Long qty) {
        return cartService.addProductToCart(cartId, productId, qty);
    }

    @PutMapping(value = {"/cart/{cartId}/remove/{productId}/{qty}"})
    public Cart removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId, @PathVariable Long qty) {
        return cartService.removeProductFromCart(cartId, productId, qty);
    }

    @DeleteMapping(value = {"/cart/cartId"})
    public void deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
    }
}
