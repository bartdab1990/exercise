package com.yapily.bart.exercise.repository;

import com.yapily.bart.exercise.model.Cart;
import com.yapily.bart.exercise.model.Product;
import com.yapily.bart.exercise.model.ProductInfo;

import java.util.List;
import java.util.Optional;

public interface ProductInfoRepository {
    Optional<ProductInfo> findExistingProductInfo(Long productId, Long cartId);

    void deleteProductInfo(Long productId, Long cartId);

    void updateProductInfoQuantity(ProductInfo existingProductInfo, ProductInfo productInfo);

    void createNewProductInfo(Product product, Cart cart, ProductInfo productInfo);

    List<ProductInfo> findAllProductInfoByCartId(Long cartId);

    Optional<ProductInfo> findProductInfoByProductId(Long productId);

    Optional<ProductInfo> saveProductInfo(ProductInfo productInfo, Product product, Cart cart);

    void deleteProductInfosByCartId(Long cartId);
}
