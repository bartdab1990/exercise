package com.yapily.bart.exercise.repository;

import com.yapily.bart.exercise.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAllProduct();

    Optional<Product> findProductByProductId(Long productId);

    Optional<Product> existingProductByName(String name);

    Optional<Product> createNewProduct(Product product, String s);

    Optional<Product> existingProductByProductId(Long productId);

    int deleteProductByProductId(Long productId);
}
