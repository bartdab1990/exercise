package com.yapily.bart.exercise.model.dto;

public class CheckoutCartDTO {
    private CartDTO cartDTO;
    private double totalCost;

    public CheckoutCartDTO() {
    }

    public CheckoutCartDTO(CartDTO cartDTO, double totalCost) {
        this.cartDTO = cartDTO;
        this.totalCost = totalCost;
    }

    public CartDTO getCartDTO() {
        return cartDTO;
    }

    public void setCartDTO(CartDTO cartDTO) {
        this.cartDTO = cartDTO;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
