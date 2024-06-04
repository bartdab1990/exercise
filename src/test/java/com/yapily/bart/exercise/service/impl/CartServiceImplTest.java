package com.yapily.bart.exercise.service.impl;

import com.yapily.bart.exercise.model.Cart;
import com.yapily.bart.exercise.model.Product;
import com.yapily.bart.exercise.model.ProductInfo;
import com.yapily.bart.exercise.repository.CartRepository;
import com.yapily.bart.exercise.repository.ProductInfoRepository;
import com.yapily.bart.exercise.repository.ProductRepository;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.util.DateUtil.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductInfoRepository productInfoRepository;

    private CartServiceImpl cartServiceImpl;

    private List<ProductInfo> productInfoList;

    private ProductInfo productInfo;

    private Product product;

    @BeforeEach
    void setUp() {
        cartServiceImpl = new CartServiceImpl(cartRepository, productRepository, productInfoRepository);

        product = Product.builder().productId(1L).addedAt(now()).name("Whisky").price(20.0).labels(List.of("drink")).build();

        productInfo = ProductInfo.builder().productId(1L).cartId(1L).quantity(1).build();
        productInfoList.add(productInfo);
    }

    @Test
    void createNewCart() {
        Cart cart = Cart.builder().cartId(1L).checkedOut(false).products(productInfoList).build();
        doReturn(Optional.of(cart)).when(cartRepository).createNewCart();
    }

    @Test
    void updateExistingCart1() {
    }

    @Test
    public void updateExistingCart_happyPath() {
        long cartId = 1L;
        long productId = 1L;
        Cart cart = Cart.builder().cartId(cartId).checkedOut(false).build();

        doReturn(Optional.of(cart)).when(cartRepository).findCartByCartId(cartId);

        doReturn(productInfoList).when(productInfoRepository).findAllProductInfoByCartId(cartId);

        doReturn(Optional.of(product)).when(productRepository).findProductByProductId(productId);

        doReturn(Optional.of(productInfo)).when(productInfoRepository).saveProductInfo(productInfo, product, cart);


        assertEquals(cartServiceImpl.updateExistingCart(1L, productInfoList).get().getProducts().size(), 1);

    }

    @Test
    void checkoutCard() {
    }

    @AfterEach
    void tearDown() {
    }
}