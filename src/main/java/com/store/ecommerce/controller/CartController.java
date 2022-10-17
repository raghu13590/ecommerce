package com.store.ecommerce.controller;

import com.store.ecommerce.model.Cart;
import com.store.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @Operation(summary = "returns all carts")
    @GetMapping(value = {"/carts"})
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @Operation(summary = "returns a cart by it's Id.")
    @GetMapping(value = {"/carts/{cartId}"})
    public Optional<Cart> getCart(@PathVariable Long cartId) {
        return cartService.getCartById(cartId);
    }

    @Operation(summary = "creates a new cart")
    @PostMapping(value = {"/carts"})
    public Cart createCart() {
        return cartService.createCart();
    }

    @Operation(summary = "adds a product to cart and calculates total")
    @PutMapping(value = {"/carts/{cartId}/add/{productId}/{qty}"})
    public Cart addProductToCart(@PathVariable Long cartId, @PathVariable Long productId, @PathVariable Long qty) {
        return cartService.addProductToCart(cartId, productId, qty);
    }

    @Operation(summary = "removes a product from cart and adjusts total")
    @PutMapping(value = {"/carts/{cartId}/remove/{productId}/{qty}"})
    public Cart removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId, @PathVariable Long qty) {
        return cartService.removeProductFromCart(cartId, productId, qty);
    }

    @Operation(summary = "deletes cart")
    @DeleteMapping(value = {"/carts/cartId"})
    public void deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
    }
}
