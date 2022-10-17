package com.store.ecommerce.service;

import com.store.ecommerce.disocuntutil.BogoFiftyCalculator;
import com.store.ecommerce.disocuntutil.DiscountFactory;
import com.store.ecommerce.disocuntutil.OffCalculator;
import com.store.ecommerce.model.Cart;
import com.store.ecommerce.model.CartItem;
import com.store.ecommerce.model.Discount;
import com.store.ecommerce.model.Product;
import com.store.ecommerce.repository.CartItemRepo;
import com.store.ecommerce.repository.CartRepo;
import com.store.ecommerce.repository.DiscountRepo;
import com.store.ecommerce.repository.ProductRepo;
import com.store.ecommerce.service.impl.CartServiceImpl;
import com.store.ecommerce.service.impl.DiscountServiceImpl;
import com.store.ecommerce.service.impl.ProductServiceImpl;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    private final Product availableProduct = new Product(100.00, 500L, true);
    private final Discount offDiscount = new Discount(Discount.DiscountType.OFF, 50);
    private final Discount bogoFiftyDiscount = new Discount(Discount.DiscountType.BOGO50, 50);
    private final Long productQtyToAdd = 10L;
    private final Long productQtyToRemove = 3L;
    private final CartItem cartItem = new CartItem();
    private final List<Cart> carts = new ArrayList<>();
    @Mock
    DiscountFactory discountFactory;
    @Mock
    CartRepo cartRepo;
    @Mock
    CartItemRepo cartItemRepo;
    @Mock
    ProductRepo productRepo;
    @Mock
    DiscountRepo discountRepo;
    @Mock
    ProductServiceImpl productServiceImpl;
    @Mock
    DiscountServiceImpl discountService;
    @InjectMocks
    CartServiceImpl cartService;
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
        assertThat(cartService.getCartById(cart.getCartId()).orElse(new Cart())
                .getCartId()).isEqualTo(cart.getCartId());
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
    public void whenAddProductWithOffDiscountToCartShouldShouldSaveInCartAndCalculateTotal() {

        // setup
        availableProduct.setProductId(1L);
        offDiscount.setDiscountId(1L);
        availableProduct.setDiscount(offDiscount);
        cart.setCartId(1L);

        when(cartRepo.findById(cart.getCartId())).thenReturn(Optional.of(cart));
        when(cartRepo.existsById(cart.getCartId())).thenReturn(true);
        when(productRepo.findById(availableProduct.getProductId())).thenReturn(Optional.of(availableProduct));
        when(productRepo.existsById(availableProduct.getProductId())).thenReturn(true);
        when(cartItemRepo.getCartItemByCartIdAndProductId(cart.getCartId(), availableProduct.getProductId())).thenReturn(Optional.of(cartItem));
        when(discountRepo.findById(offDiscount.getDiscountId())).thenReturn(Optional.of(offDiscount));
        when(discountFactory.getDiscountCalculator(offDiscount.getDiscountType())).thenReturn(new OffCalculator());

        Double initialCartTotal = cart.getTotal();
        cart = cartService.addProductToCart(cart.getCartId(), availableProduct.getProductId(), productQtyToAdd);

        // assert
        assertThat(cart.getItemsInCart().stream().map(CartItem::getProduct).collect(Collectors.toList())
                .contains(availableProduct)).isEqualTo(true);

        assertThat(initialCartTotal + cart.getTotal()).isEqualTo(initialCartTotal + (availableProduct.getPrice()/2 * productQtyToAdd));
        verify(productRepo).save(availableProduct);
        verify(cartItemRepo).save(cartItem);
        verify(cartRepo).save(cart);
    }

    @Test
    public void whenAddProductWithBogoFiftyDiscountToCartShouldShouldSaveInCartAndCalculateTotal() {

        // setup
        availableProduct.setProductId(1L);
        offDiscount.setDiscountId(1L);
        availableProduct.setDiscount(offDiscount);
        cart.setCartId(1L);

        when(cartRepo.findById(cart.getCartId())).thenReturn(Optional.of(cart));
        when(cartRepo.existsById(cart.getCartId())).thenReturn(true);
        when(productRepo.findById(availableProduct.getProductId())).thenReturn(Optional.of(availableProduct));
        when(productRepo.existsById(availableProduct.getProductId())).thenReturn(true);
        when(cartItemRepo.getCartItemByCartIdAndProductId(cart.getCartId(), availableProduct.getProductId())).thenReturn(Optional.of(cartItem));
        when(discountRepo.findById(offDiscount.getDiscountId())).thenReturn(Optional.of(offDiscount));
        when(discountFactory.getDiscountCalculator(offDiscount.getDiscountType())).thenReturn(new BogoFiftyCalculator());

        Double initialCartTotal = cart.getTotal();
        cart = cartService.addProductToCart(cart.getCartId(), availableProduct.getProductId(), productQtyToAdd);

        // assert
        assertThat(cart.getItemsInCart().stream().map(CartItem::getProduct).collect(Collectors.toList())
                .contains(availableProduct)).isEqualTo(true);

        Double exceptedTotal;
        if (productQtyToAdd % 2 == 0) {
            exceptedTotal = availableProduct.getPrice() * (productQtyToAdd / 2) + availableProduct.getPrice() * (0.5) * (productQtyToAdd / 2);
        } else {
            exceptedTotal = productQtyToAdd * ((productQtyToAdd / 2) + 1) + availableProduct.getPrice() * (0.5) * (productQtyToAdd / 2);
        }

        assertThat(initialCartTotal + cart.getTotal()).isEqualTo(initialCartTotal + (exceptedTotal));
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
            assertThat(returnedCartItem.orElse(new CartItem())
                    .getQuantity()).isEqualTo(productQtyToAdd - productQtyToRemove);
            assertThat(returnedCartItem.orElse(new CartItem()).getTotal())
                    .isEqualTo(availableProduct.getPrice() * (cartItem.getQuantity()));
            verify(cartItemRepo).save(cartItem);
        }

        assertThat(cart.getTotal()).isEqualTo(initialCartTotal - (availableProduct.getPrice() * productQtyToRemove));
        verify(productRepo).save(availableProduct);
    }
}
