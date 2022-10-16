package com.store.ecommerce.service;

import com.store.ecommerce.model.Discount;
import com.store.ecommerce.repository.DiscountRepo;
import com.store.ecommerce.service.impl.DiscountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiscountServiceTest {

    private final Discount discount = new Discount(Discount.DiscountType.OFF, 50);
    @Mock
    private DiscountRepo discountRepo;
    @InjectMocks
    private DiscountServiceImpl discountService;

    @Test
    public void whenGetAllDiscountsShouldGetDiscounts() {

        // setup
        List<Discount> discounts = new ArrayList<>();
        when(discountRepo.findAll()).thenReturn(discounts);

        // execute
        List<Discount> returnedDiscounts = discountService.getAllDiscounts();

        // assert
        assertThat(returnedDiscounts).isSameAs(discounts);
        verify(discountRepo).findAll();
    }

    @Test
    public void whenGetDiscountByIdShouldReturnDiscount() {

        // setup
        discount.setDiscountId(1L);
        when(discountRepo.findById(discount.getDiscountId())).thenReturn(Optional.of(discount));

        // execute
        Discount returnedDiscount = discountService.getDiscountById(discount.getDiscountId()).orElse(null);

        // assert
        assertThat(returnedDiscount).isSameAs(discount);
        verify(discountRepo).findById(discount.getDiscountId());
    }

    @Test
    public void whenCreateDiscountShouldReturnCreatedDiscount() {

        // setup
        discount.setDiscountId(1L);
        when(discountRepo.save(discount)).thenReturn(discount);

        // execute
        Discount createdDiscount = discountService.createDiscount(discount);

        // assert
        assertThat(createdDiscount).isSameAs(discount);
        verify(discountRepo).save(discount);
    }
}
