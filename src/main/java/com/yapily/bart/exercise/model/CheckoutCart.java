package com.yapily.bart.exercise.model;

import lombok.Builder;

@Builder
public class CheckoutCart {
    private Cart cart;
    private double totalCost;

    public CheckoutCart() {
    }

    public CheckoutCart(Cart cart, double totalCost) {
        this.cart = cart;
        this.totalCost = totalCost;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "CheckoutCart{" +
                "cart=" + cart +
                ", totalCost=" + totalCost +
                '}';
    }
}
