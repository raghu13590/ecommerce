package com.store.ecommerce.service.impl;

import com.store.ecommerce.exception.EcommerceException;
import com.store.ecommerce.model.Discount;
import com.store.ecommerce.model.Product;
import com.store.ecommerce.repository.DiscountRepo;
import com.store.ecommerce.repository.ProductRepo;
import com.store.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private DiscountRepo discountRepo;

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product addProduct(Product product) {
        checkProductNameAlreadyInUse(product);
        return productRepo.save(product);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepo.findById(id);
    }

    @Override
    public Product updateProduct(Product product, Long productId) {
        checkProductExists(productId);
        Product existingProduct = productRepo.findById(productId).orElseThrow();
        checkProductNameAlreadyInUse(existingProduct);

        existingProduct.setName(product.getName());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setActive(product.isActive());
        existingProduct.setDiscount(product.getDiscount());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPictureUrl(product.getPictureUrl());
        existingProduct.setPrice(product.getPrice());
        return productRepo.save(existingProduct);
    }

    @Override
    public Product addDiscountToProduct(Long productId, Long discountId) {
        checkProductExists(productId);
        Product product = productRepo.findById(productId).orElseThrow();
        Discount discount = discountRepo.findById(discountId).orElseThrow();
        product.setDiscount(discount);
        return productRepo.save(product);
    }

    @Override
    public Product deActivateProduct(Long productId) {
        checkProductExists(productId);
        Product product = productRepo.findById(productId).orElseThrow();
        product.setActive(false);
        return productRepo.save(product);
    }

    private void checkProductExists(Long productId) {
        if (!productRepo.existsById(productId)) {
            throw new EcommerceException("product doesn't exist");
        }
    }

    private void checkProductNameAlreadyInUse(Product product) {
        Optional<Product> existingProduct = productRepo.getProductByName(product.getName());
        if (existingProduct.isPresent() && !existingProduct.get().getProductId().equals(product.getProductId())) {
            throw new EcommerceException("product name already in use, please use a different name");
        }
    }

    @Override
    public Product removeDiscountFromProduct(Long productId) {
        checkProductExists(productId);
        Product product = productRepo.findById(productId).orElseThrow();
        product.setDiscount(null);
        return productRepo.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        checkProductExists(productId);
        productRepo.deleteById(productId);
    }

    @Override
    public List<Product> getAllActiveProducts() {
        return productRepo.findAll().stream().filter(product -> product.isActive().equals(true))
                .collect(Collectors.toList());
    }
}
