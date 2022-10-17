package com.store.ecommerce.controller;

import com.store.ecommerce.model.Product;
import com.store.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "returns all products")
    @GetMapping(value = {"/products"})
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "returns all active products")
    @GetMapping(value = {"/products/active"})
    public List<Product> getAllActiveProducts() {
        return productService.getAllActiveProducts();
    }

    @Operation(summary = "returns a product by its' Id.")
    @GetMapping(value = {"/products/{productId}"})
    public Optional<Product> getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @Operation(summary = "creates a new product")
    @PostMapping(value = {"/products"})
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @Operation(summary = "updates a product")
    @PutMapping(value = {"/products/{productId}"})
    public Product updateProduct(@RequestBody Product product, @PathVariable Long productId) {
        return productService.updateProduct(product, productId);
    }

    @Operation(summary = "adds discount a product")
    @PutMapping(value = {"/products/{productId}/addDiscount/{discountId}"})
    public Product addDiscountToProduct(@PathVariable Long productId, @PathVariable Long discountId) {
        return productService.addDiscountToProduct(productId, discountId);
    }

    @Operation(summary = "removes any discount added to a product")
    @PutMapping(value = {"/products/{productId}/removeDiscount"})
    public Product removeDiscountFromProduct(@PathVariable Long productId) {
        return productService.removeDiscountFromProduct(productId);
    }

    @Operation(summary = "deactivates a product")
    @PutMapping(value = {"/products/deActivate/{productId}"})
    public Product deActivate(@PathVariable Long productId) {
        return productService.deActivateProduct(productId);
    }

    @Operation(summary = "deletes a product")
    @DeleteMapping(value = {"/products/{productId}"})
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}