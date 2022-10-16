package com.store.ecommerce.service;

import com.store.ecommerce.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    Product addProduct(Product product);

    Optional<Product> getProductById(Long id);

    Product updateProduct(Product product, Long productId);

    Product addDiscountToProduct(Long productId, Long discountId);

    Product removeProduct(Long productId);

    Product removeDiscountFromProduct(Long productId);

    void deleteProduct(Long productId);
}
