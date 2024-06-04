package com.yapily.bart.exercise.controller;

import com.yapily.bart.exercise.mapper.CartMapper;
import com.yapily.bart.exercise.mapper.CheckoutCartMapper;
import com.yapily.bart.exercise.model.Cart;
import com.yapily.bart.exercise.model.CheckoutCart;
import com.yapily.bart.exercise.model.ProductInfo;
import com.yapily.bart.exercise.model.dto.CartDTO;
import com.yapily.bart.exercise.model.dto.CheckoutCartDTO;
import com.yapily.bart.exercise.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CartController {

    static final Logger log = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;
    private final CartMapper cartMapper;
    private final CheckoutCartMapper checkoutCartMapper;

    public CartController(CartService cartService, CartMapper cartMapper, CheckoutCartMapper checkoutCartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
        this.checkoutCartMapper = checkoutCartMapper;
    }

    @GetMapping("/carts")
    ResponseEntity<List<CartDTO>> getAllOfCarts() {
        try {
            List<Cart> persistedCart = cartService.getListAllCarts();
            return persistedCart.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(persistedCart.stream().map(cartMapper::convertCartEntityToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/carts")
    ResponseEntity<CartDTO> createNewCart() {
        try {
            Optional<Cart> persistedCart = cartService.createNewCart();
            return persistedCart.map(cart -> ResponseEntity.status(201).body(cartMapper.convertCartEntityToDTO(cart))).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/carts/{cartId}")
    ResponseEntity<CartDTO> updateCart(@PathVariable("cartId") Long cartId, @RequestBody List<ProductInfo> productInfoList) {
        try {
            Optional<Cart> persistedCart = cartService.updateExistingCart(cartId, productInfoList);
            return persistedCart.map(cart -> ResponseEntity.status(201).body(cartMapper.convertCartEntityToDTO(cart))).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/carts/{cartId}/checkout")
    ResponseEntity<CheckoutCartDTO> checkoutCart(@PathVariable("cartId") Long cartId) {
        try {
            Optional<CheckoutCart> persistedCart = cartService.checkoutCard(cartId);
            return persistedCart.map(cart -> ResponseEntity.status(201).body(checkoutCartMapper.convertCheckoutCartEntityToDTO(cart))).orElseGet(() -> ResponseEntity.unprocessableEntity().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
