package com.yapily.bart.exercise.service;

import com.yapily.bart.exercise.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAllProducts();

    Optional<Product> findProductByProductId(Long productId);

    Optional<Product> createNewProduct(Product product);

    int deleteProductByProductId(Long productId);
}
