package com.yapily.bart.exercise.repository;

import com.yapily.bart.exercise.model.Cart;

import java.util.List;
import java.util.Optional;

public interface CartRepository {
    Optional<Cart> createNewCart();

    Optional<Cart> findCartByCartId(Long cartId);
    int startCheckout(Long cartId, boolean b);

    double calculateTotalCostForCart(Long cartId);

    void updateTotalCost(double totalCost, Long cartId);
    List<Cart> findAllCarts();

    void revertCheckoutChanges(Long cartId);
}
