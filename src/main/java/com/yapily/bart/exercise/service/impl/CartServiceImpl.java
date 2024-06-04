package com.yapily.bart.exercise.service.impl;

import com.yapily.bart.exercise.model.Cart;
import com.yapily.bart.exercise.model.CheckoutCart;
import com.yapily.bart.exercise.model.Product;
import com.yapily.bart.exercise.model.ProductInfo;
import com.yapily.bart.exercise.repository.CartRepository;
import com.yapily.bart.exercise.repository.ProductInfoRepository;
import com.yapily.bart.exercise.repository.ProductRepository;
import com.yapily.bart.exercise.service.CartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ProductInfoRepository productInfoRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, ProductInfoRepository productInfoRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.productInfoRepository = productInfoRepository;
    }

    @Override
    public Optional<Cart> createNewCart() {
        return cartRepository.createNewCart();
    }

    @Override
    public Optional<Cart> updateExistingCart(Long cartId, List<ProductInfo> productInfoList) {
        List<ProductInfo> persistedProductInfoList = new ArrayList<>();
        try {
            Optional<Cart> existingCart = cartRepository.findCartByCartId(cartId);
            if (existingCart.isPresent() && !existingCart.get().isCheckedOut()) {
                List<ProductInfo> existingProductInfoList = productInfoRepository.findAllProductInfoByCartId(cartId);
                if (existingProductInfoList.isEmpty()) {
                    savingListOfProductInfo(productInfoList, persistedProductInfoList, existingCart);
                } else {
                    productInfoRepository.deleteProductInfosByCartId(cartId);
                    savingListOfProductInfo(productInfoList, persistedProductInfoList, existingCart);
                }
                existingCart.get().setProducts(persistedProductInfoList);
                return existingCart;
            }
            return Optional.empty();
        } catch (Exception e) {
            productInfoRepository.deleteProductInfosByCartId(cartId);
            return Optional.empty();
        }
    }

    private void savingListOfProductInfo(List<ProductInfo> productInfoList, List<ProductInfo> persistedProductInfoList, Optional<Cart> existingCart) {
        for (ProductInfo productInfo : productInfoList) {
            Optional<Product> existingProduct = productRepository.findProductByProductId(productInfo.getProductId());
            if (existingProduct.isPresent()) {
                Optional<ProductInfo> persistedProductInfo = productInfoRepository.saveProductInfo(productInfo, existingProduct.get(), existingCart.get());
                persistedProductInfo.ifPresent(persistedProductInfoList::add);
            } else {
                //display log - product not exist and cannot be persisted
            }
        }
    }

    @Override
    public Optional<CheckoutCart> checkoutCard(Long cartId) {
        try {
            CheckoutCart checkoutCart = new CheckoutCart();
            Optional<Cart> existingCart = cartRepository.findCartByCartId(cartId);
            if (existingCart.isPresent() && !existingCart.get().isCheckedOut()) {
                List<ProductInfo> productInfoList = productInfoRepository.findAllProductInfoByCartId(cartId);
                if (!productInfoList.isEmpty()) {
                    existingCart.get().setProducts(productInfoList);
                    int i = cartRepository.startCheckout(cartId, true);
                    if (i == 1) {
                        existingCart.get().setCheckedOut(true);
                        double totalCost = cartRepository.calculateTotalCostForCart(cartId);
                        if (totalCost > 0) {
                            cartRepository.updateTotalCost(totalCost, cartId);
                            checkoutCart.setCart(existingCart.get());
                            checkoutCart.setTotalCost(totalCost);
                            return Optional.of(checkoutCart);
                        }
                    }
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            cartRepository.revertCheckoutChanges(cartId);
            return Optional.empty();
        }

    }

    @Override
    public List<Cart> getListAllCarts() {
        return cartRepository.findAllCarts();
    }
}
