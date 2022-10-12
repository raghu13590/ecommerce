package com.store.ecommerce.controller;

import com.store.ecommerce.model.Product;
import com.store.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = {"/products"})
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(value = {"/product/{productId}"})
    public Optional<Product> getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping(value = {"/product"})
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping(value = {"/product/{productId}"})
    public Product updateProduct(@RequestBody Product product, @PathVariable Long productId) {
        return productService.updateProduct(product, productId);
    }

    @PutMapping(value = {"/product/{productId}/addDiscount/{discountId}"})
    public Product addDiscountToProduct(@PathVariable Long productId, @PathVariable Long discountId) {
        return productService.addDiscountToProduct(productId, discountId);
    }

    @PutMapping(value = {"/product/{productId}/removeDiscount"})
    public Product removeDiscountFromProduct(@PathVariable Long productId) {
        return productService.removeDiscountFromProduct(productId);
    }

    @PutMapping(value = {"/product/remove/{productId}"})
    public Product removeProduct(@PathVariable Long productId) {
        return productService.removeProduct(productId);
    }

    @DeleteMapping(value = {"/product/{productId}"})
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}