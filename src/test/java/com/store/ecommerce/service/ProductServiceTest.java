package com.store.ecommerce.service;

import com.store.ecommerce.model.Discount;
import com.store.ecommerce.model.Product;
import com.store.ecommerce.repository.DiscountRepo;
import com.store.ecommerce.repository.ProductRepo;
import com.store.ecommerce.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private final List<Product> productList = new ArrayList<>();
    private final Product availableProduct = new Product(100.00, 10L, true);
    private final Product inActiveProduct = new Product(200.00, 20L, false);
    private final Discount discount = new Discount(Discount.DiscountType.OFF, 30);
    @Mock
    private DiscountRepo discountRepo;
    @Mock
    private ProductRepo productRepo;
    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Test
    public void whenGetAllProductsShouldReturnListOfProducts() {

        when(productRepo.findAll()).thenReturn(productList);

        assertThat(productServiceImpl.getAllProducts()).isSameAs(productList);
    }

    @Test
    public void whenGetProductByIdShouldReturnProduct() {
        availableProduct.setProductId(1L);
        when(productRepo.findById(availableProduct.getProductId())).thenReturn(Optional.of(availableProduct));

        Product returnedProduct = productServiceImpl.getProductById(availableProduct.getProductId())
                .orElse(new Product());
        assertThat(returnedProduct).isSameAs(availableProduct);
    }

    @Test
    public void whenUpdateProductShouldChangeProductContents() {
        // setup
        availableProduct.setProductId(1L);
        Product updateProduct = new Product(500.00, 50L, false);
        updateProduct.setName("updatedName");
        updateProduct.setDiscount(discount);
        updateProduct.setPictureUrl("updatedPictureUrl");
        updateProduct.setDescription("updatedDescription");
        when(productRepo.save(availableProduct)).thenReturn(availableProduct);
        when(productRepo.findById(availableProduct.getProductId())).thenReturn(Optional.of(availableProduct));
        when(productRepo.existsById(availableProduct.getProductId())).thenReturn(true);

        // execute
        Product updatedProduct = productServiceImpl.updateProduct(updateProduct, availableProduct.getProductId());

        // assert
        assertThat(updatedProduct.getProductId()).isEqualTo(availableProduct.getProductId());
        assertThat(updatedProduct.getName()).isEqualTo(updateProduct.getName());
        assertThat(updatedProduct.getDescription()).isEqualTo(updatedProduct.getDescription());
        assertThat(updatedProduct.getPrice()).isEqualTo(updateProduct.getPrice());
        assertThat(updatedProduct.getQuantity()).isEqualTo(updateProduct.getQuantity());
        assertThat(updatedProduct.getPictureUrl()).isEqualTo(updateProduct.getPictureUrl());
        assertThat(updatedProduct.getDiscount()).isEqualTo(updateProduct.getDiscount());
    }

    @Test
    public void whenAddDiscountToProductShouldAddDiscount() {

        // setup
        availableProduct.setProductId(1L);
        discount.setDiscountId(1L);
        when(productRepo.findById(availableProduct.getProductId())).thenReturn(Optional.of(availableProduct));
        when(productRepo.existsById(availableProduct.getProductId())).thenReturn(true);
        when(discountRepo.findById(discount.getDiscountId())).thenReturn(Optional.of(discount));
        when(productRepo.save(availableProduct)).thenReturn(availableProduct);

        // execute
        Product productWithDiscount = productServiceImpl.addDiscountToProduct(availableProduct.getProductId(), discount.getDiscountId());

        // assert
        assertThat(productWithDiscount.getDiscount()).isSameAs(discount);
    }

    @Test
    public void whenRemoveProductShouldDeActivateProduct() {

        // setup
        availableProduct.setProductId(1L);
        discount.setDiscountId(1L);
        when(productRepo.findById(availableProduct.getProductId())).thenReturn(Optional.of(availableProduct));
        when(productRepo.existsById(availableProduct.getProductId())).thenReturn(true);
        when(productRepo.save(availableProduct)).thenReturn(availableProduct);

        // execute
        Product removedProduct = productServiceImpl.deActivateProduct(availableProduct.getProductId());

        // assert
        assertThat(removedProduct.isActive()).isEqualTo(false);
    }

    @Test
    public void whenRemoveDiscountFromProductShouldRemoveDiscount() {

        // setup
        availableProduct.setProductId(1L);
        availableProduct.setDiscount(discount);
        when(productRepo.findById(availableProduct.getProductId())).thenReturn(Optional.of(availableProduct));
        when(productRepo.existsById(availableProduct.getProductId())).thenReturn(true);
        when(productRepo.save(availableProduct)).thenReturn(availableProduct);

        // execute
        Product productWithDiscountRemoved = productServiceImpl.removeDiscountFromProduct(availableProduct.getProductId());

        // assert
        assertThat(productWithDiscountRemoved.getDiscount()).isEqualTo(null);
    }
}
