package com.store.ecommerce.service;

import com.store.ecommerce.model.Cart;
import com.store.ecommerce.model.CartItem;
import com.store.ecommerce.model.Product;
import com.store.ecommerce.repository.CartItemRepo;
import com.store.ecommerce.repository.CartRepo;
import com.store.ecommerce.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    private final Product availableProduct = new Product(100.00, 500L, true);
    private final Long productQtyToAdd = 10L;
    private final Long productQtyToRemove = 3L;
    private final CartItem cartItem = new CartItem();
    private final List<Cart> carts = new ArrayList<>();
    @Mock
    CartRepo cartRepo;
    @Mock
    CartItemRepo cartItemRepo;
    @Mock
    ProductRepo productRepo;
    @Mock
    ProductService productService;
    @Mock
    DiscountService discountService;
    @InjectMocks
    CartService cartService;
    private Cart cart = new Cart();

    @Test
    public void whenGetAllCartsShouldReturnListOfCart() {

        // setup
        when(cartRepo.findAll()).thenReturn(carts);

        // assert
        assertThat(cartService.getAllCarts()).isSameAs(carts);
        verify(cartRepo).findAll();
    }

    @Test
    public void whenGetCartByIdShouldReturnCart() {

        // setup
        cart.setCartId(1L);
        when(cartRepo.findById(cart.getCartId())).thenReturn(Optional.ofNullable(cart));

        // assert
        assertThat(cartService.getCartById(cart.getCartId()).orElse(new Cart()).getCartId()).isEqualTo(cart.getCartId());
        verify(cartRepo).findById(cart.getCartId());
    }

    @Test
    public void whenCreateCartShouldReturnCartType() {

        // setup
        when(cartRepo.save(any(Cart.class))).thenReturn(cart);
        Cart returnedCart = cartService.createCart();

        // assert
        assertThat(cart).isSameAs(returnedCart);
        verify(cartRepo).save(any());
    }

    @Test
    public void whenAddProductToCartShouldShouldSaveInCartAndCalculateTotal() {

        // setup
        availableProduct.setProductId(1L);
        cart.setCartId(1L);

        when(cartRepo.findById(cart.getCartId())).thenReturn(Optional.of(cart));
        when(cartRepo.existsById(cart.getCartId())).thenReturn(true);
        when(productRepo.findById(availableProduct.getProductId())).thenReturn(Optional.of(availableProduct));
        when(productRepo.existsById(availableProduct.getProductId())).thenReturn(true);
        when(cartItemRepo.getCartItemByCartIdAndProductId(cart.getCartId(), availableProduct.getProductId())).thenReturn(Optional.of(cartItem));

        Double initialCartTotal = cart.getTotal();
        cart = cartService.addProductToCart(cart.getCartId(), availableProduct.getProductId(), productQtyToAdd);

        // assert
        assertThat(cart.getItemsInCart().stream().map(CartItem::getProduct).collect(Collectors.toList())
                .contains(availableProduct)).isEqualTo(true);

        assertThat(initialCartTotal + cart.getTotal()).isEqualTo(availableProduct.getPrice() * productQtyToAdd);
        verify(productRepo).save(availableProduct);
        verify(cartItemRepo).save(cartItem);
        verify(cartRepo).save(cart);
    }

    @Test
    public void removeProductFromCartShouldRemoveProductAndUpdateTotal() {

        // setup
        availableProduct.setProductId(1L);
        cart.setCartId(1L);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(availableProduct);
        cartItem.setQuantity(productQtyToAdd);
        cartItem.setTotal(availableProduct.getPrice() * cartItem.getQuantity());
        cart.getItemsInCart().add(cartItem);
        cart.setTotal(cart.getItemsInCart().stream().mapToDouble(CartItem::getTotal).sum());

        when(cartRepo.findById(cart.getCartId())).thenReturn(Optional.of(cart));
        when(cartRepo.existsById(cart.getCartId())).thenReturn(true);
        when(productRepo.findById(availableProduct.getProductId())).thenReturn(Optional.of(availableProduct));
        when(productRepo.existsById(availableProduct.getProductId())).thenReturn(true);
        when(cartItemRepo.getCartItemByCartIdAndProductId(cart.getCartId(), availableProduct.getProductId())).thenReturn(Optional.of(cartItem));

        Double initialCartTotal = cart.getTotal();
        cart = cartService.removeProductFromCart(cart.getCartId(), availableProduct.getProductId(), productQtyToRemove);

        // assert
        Optional<CartItem> returnedCartItem = cart.getItemsInCart().stream()
                .filter(item -> item.getProduct().equals(availableProduct)).findFirst();
        if (Objects.equals(cartItem.getQuantity(), productQtyToRemove)) {
            assertThat(returnedCartItem.isEmpty()).isEqualTo(true);
            verify(cartItemRepo).delete(cartItem);
        } else {
            assertThat(returnedCartItem.orElse(new CartItem()).getQuantity()).isEqualTo(productQtyToAdd - productQtyToRemove);
            assertThat(returnedCartItem.orElse(new CartItem()).getTotal())
                    .isEqualTo(availableProduct.getPrice() * (cartItem.getQuantity()));
            verify(cartItemRepo).save(cartItem);
        }

        assertThat(cart.getTotal()).isEqualTo(initialCartTotal - (availableProduct.getPrice() * productQtyToRemove));
        verify(productRepo).save(availableProduct);
    }
}
