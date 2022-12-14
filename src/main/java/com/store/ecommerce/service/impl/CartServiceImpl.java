package com.store.ecommerce.service.impl;

import com.store.ecommerce.deal.DealProcessor;
import com.store.ecommerce.deal.IDealProcessor;
import com.store.ecommerce.disocuntutil.DiscountCalculator;
import com.store.ecommerce.disocuntutil.DiscountFactory;
import com.store.ecommerce.exception.EcommerceException;
import com.store.ecommerce.model.*;
import com.store.ecommerce.repository.*;
import com.store.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private DiscountRepo discountRepo;

    @Autowired
    private DiscountFactory discountFactory;

    @Autowired
    private DealRepo dealRepo;

    @Autowired
    private ProductDealRepo productDealRepo;

    @Autowired
    private IDealProcessor dealProcessor;

    @Override
    public List<Cart> getAllCarts() {
        return cartRepo.findAll();
    }

    @Override
    public Optional<Cart> getCartById(Long cartId) {
        return cartRepo.findById(cartId);
    }

    @Override
    public Cart createCart() {
        Cart cart = new Cart();
        return cartRepo.save(cart);
    }

    @Transactional
    @Override
    public Cart addProductToCart(Long cartId, Long productId, Long qty) {
        // validations
        validateCartAndProduct(cartId, productId);

        Cart cart = cartRepo.findById(cartId).orElseThrow();
        Product product = productRepo.findById(productId).orElseThrow();

        if (product.getQuantity() < qty) {
            throw new EcommerceException("insufficient items in inventory");
        }

        // create an item in cart
        CartItem cartItem = new CartItem();
        if (cartItemRepo.getCartItemByCartIdAndProductId(cartId, productId).isPresent()) {
            cartItem = cartItemRepo.getCartItemByCartIdAndProductId(cartId, productId).get();
        }
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItem.getQuantity() + qty);
        cartItem.setTotal(calculateProductTotal(product, cartItem.getQuantity()));
        cartItemRepo.save(cartItem);

        // update product quantity in inventory
        product.setQuantity(product.getQuantity() - qty);
        productRepo.save(product);

        // add item to cart and calculate total order cost
        cart.getItemsInCart().add(cartItem);
        cart.setTotal(calculateCartTotal(cart.getItemsInCart()));
        cart.setLastUpdate(LocalDate.now());
        cartRepo.save(cart);
        return cart;
    }

    /***
     * checks if cart exists and product exists and is active
     * @param cartId
     * @param productId
     */
    private void validateCartAndProduct(Long cartId, Long productId) {
        validateCart(cartId);

        if (!productRepo.existsById(productId) || (productRepo.findById(productId)
                .isPresent() && !productRepo.findById(productId).get().isActive())) {
            throw new EcommerceException("product doesn't exist or not available");
        }
    }

    /***
     * checks if cart exists
     * @param cartId
     */
    private void validateCart(Long cartId) {
        if (!cartRepo.existsById(cartId)) {
            throw new EcommerceException("cart doesn't exist");
        }
    }

    /***
     * returns total of all items in cart
     * @param itemsInCart
     * @return
     */
    private Double calculateCartTotal(Set<CartItem> itemsInCart) {
        return itemsInCart.stream().mapToDouble(CartItem::getTotal).sum();
    }

    /***
     * calculates total of a product with the discount if any applicable
     * @param product
     * @param qty
     * @return
     */
    private Double calculateProductTotal(Product product, Long qty) {
        Double total = product.getPrice() * qty;

        if (product.getDiscount() == null || product.getDiscount().getDiscountId() == null) {
            return total;
        }

        Optional<Discount> discount = discountRepo.findById(product.getDiscount().getDiscountId());
        if (discount.isPresent()) {
            DiscountCalculator discountCalc = discountFactory.getDiscountCalculator(discount.get()
                    .getDiscountType());
            total = discountCalc.getDiscount(discount.get().getPercentageOff(), product.getPrice(), qty);
        }
        return total;
    }

    @Override
    public void deleteCart(Long cartId) {
        if (cartRepo.existsById(cartId)) {
            cartRepo.deleteById(cartId);
        } else {
            throw new EcommerceException("cart doesn't exist");
        }
    }

    @Transactional
    @Override
    public Cart removeProductFromCart(Long cartId, Long productId, Long qty) {
        validateCartAndProduct(cartId, productId);

        if (cartItemRepo.getCartItemByCartIdAndProductId(cartId, productId).isEmpty()) {
            throw new EcommerceException("product not in cart, cannot remove it");
        }

        Cart cart = cartRepo.findById(cartId).orElseThrow();
        Product product = productRepo.findById(productId).orElseThrow();

        // find cart items from cart for corresponding cart
        CartItem cartItem = cartItemRepo.getCartItemByCartIdAndProductId(cartId, productId).get();

        // check if enough items are in cart to be removed
        if (cartItem.getQuantity() < qty) {
            throw new RuntimeException("not enough items in cart to remove");
        }

        // remove items and calculate new total
        cartItem.setQuantity(cartItem.getQuantity() - qty);
        if (cartItem.getQuantity() == 0) {
            cart.getItemsInCart().remove(cartItem);
            cartItemRepo.delete(cartItem);
        } else {
            cartItem.setTotal(calculateProductTotal(product, cartItem.getQuantity()));
            cartItemRepo.save(cartItem);
        }

        // update product quantity in inventory
        product.setQuantity(product.getQuantity() + qty);
        productRepo.save(product);

        // remove items from cart and adjust the total
        cart.setTotal(calculateCartTotal(cart.getItemsInCart()));
        cart.setLastUpdate(LocalDate.now());
        cartRepo.save(cart);
        return cart;
    }

    @Override
    public Cart checkout(Long cartId) {

        validateCart(cartId);
        Cart cart = cartRepo.findById(cartId).orElseThrow();

        Set<Deal> deals = dealProcessor.getDeals(cart);
        cart = dealProcessor.applyDeals(cart, deals);
        cartRepo.save(cart);
        return cart;
    }
}
