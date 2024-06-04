package com.yapily.bart.exercise.model.dto;

import java.util.List;

public class CartDTO {
    private Long cartId;
    private List<ProductInfoDTO> products;
    private boolean checkedOut;

    public CartDTO() {
    }

    public CartDTO(Long cartId, List<ProductInfoDTO> products, boolean checkedOut) {
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

    public List<ProductInfoDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfoDTO> products) {
        this.products = products;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }
}

