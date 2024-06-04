package com.yapily.bart.exercise.service;

import com.yapily.bart.exercise.model.Cart;
import com.yapily.bart.exercise.model.CheckoutCart;
import com.yapily.bart.exercise.model.ProductInfo;

import java.util.List;
import java.util.Optional;

public interface CartService {
    Optional<Cart> createNewCart();
    Optional<Cart> updateExistingCart(Long cartId, List<ProductInfo> productInfoList);
    Optional<CheckoutCart> checkoutCard(Long cartId);

    List<Cart> getListAllCarts();
}
