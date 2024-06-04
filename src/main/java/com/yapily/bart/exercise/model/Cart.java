package com.yapily.bart.exercise.model;

import lombok.Builder;

import java.util.List;

@Builder
public class Cart {

    private Long cartId;
    private List<ProductInfo> products;
    private boolean checkedOut;

    public Cart() {
    }

    public Cart(Long cartId, boolean checkedOut) {
        this.cartId = cartId;
        this.checkedOut = checkedOut;
    }

    public Cart(Long cartId, List<ProductInfo> products, boolean checkedOut) {
        this.cartId = cartId;
        this.products = products;
        this.checkedOut = checkedOut;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public List<ProductInfo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfo> products) {
        this.products = products;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }
}
