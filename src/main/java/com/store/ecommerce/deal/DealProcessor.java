package com.store.ecommerce.deal;

import com.store.ecommerce.model.*;
import com.store.ecommerce.repository.CartItemRepo;
import com.store.ecommerce.repository.DealRepo;
import com.store.ecommerce.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Component
public class DealProcessor implements IDealProcessor {

    @Autowired
    DealRepo dealRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CartItemRepo cartItemRepo;

    @Override
    public Set<Deal> getDeals(Cart cart) {
        Set<Deal> applicableDeals = new HashSet<>();
        cart.getItemsInCart().forEach(cartItem -> applicableDeals.addAll(dealRepo.findByProductId(cartItem.getProduct().getProductId())));
        return applicableDeals;
    }

    @Override
    public Cart applyDeals(Cart cart, Set<Deal> deals) {

        // get items in cart
        Map<Product, Long> productsInCart = new HashMap<>();
        cart.getItemsInCart().forEach(cartItem -> productsInCart.put(cartItem.getProduct(), productsInCart.getOrDefault(cartItem.getProduct(), 0L)));

        List<Product> freeItems = new ArrayList<>();

        // process each deal
        for (Deal deal : deals) {
            // get eligible items in deal
            Set<Product> eligibleProducts = getEligibleProductsFromDeal(deal);

            // check if cart contains all the eligible items
            Integer eligibleProductsCt = 0;
            for (Product eligibleProduct : eligibleProducts) {
                if(productsInCart.containsKey(eligibleProduct) && productsInCart.get(eligibleProduct) > 0){
                    eligibleProductsCt ++;
                }
            }

            if (eligibleProductsCt.equals(eligibleProducts.size())) {
                // add to free to cart
                deal.getProductDeals().stream()
                        .filter(productDeal -> productDeal.getProductDealType().equals(ProductDeal.ProductDealType.FREE_ITEM))
                        .forEach(productDeal -> processFreeItem(cart, productDeal.getProduct()));

                // mark eligible items as used
                deal.getProductDeals().stream()
                        .filter(productDeal -> productDeal.getProductDealType().equals(ProductDeal.ProductDealType.ELIGIBLE_ITEM))
                        .forEach(productDeal -> productsInCart.put(productDeal.getProduct(), productsInCart.get(productDeal.getProduct()) - 1));
            }
        }

        return cart;
    }

    @Transactional
    private void processFreeItem(Cart cart, Product freeItem) {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(freeItem);
        cartItem.setQuantity(1L);
        cartItem.setTotal(0.00);
        cartItem.setCart(cart);
        cartItemRepo.save(cartItem);
        cart.getItemsInCart().add(cartItem);
        Product product = productRepo.findById(freeItem.getProductId()).orElseThrow();
        product.setQuantity(product.getQuantity() - 1);
        productRepo.save(product);
    }

    private Set<Product> getEligibleProductsFromDeal(Deal deal) {
        Set<Product> eligibleProducts = new HashSet<>();
        deal.getProductDeals().forEach(productDeal -> {
            if (productDeal.getProductDealType().equals(ProductDeal.ProductDealType.ELIGIBLE_ITEM)){
                eligibleProducts.add(productDeal.getProduct());
            }
        });
        return eligibleProducts;
    }
}
