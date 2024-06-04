package com.yapily.bart.exercise.repository.impl;

import com.yapily.bart.exercise.model.Cart;
import com.yapily.bart.exercise.model.Product;
import com.yapily.bart.exercise.model.ProductInfo;
import com.yapily.bart.exercise.repository.ProductInfoRepository;
import com.yapily.bart.exercise.repository.mapper.ProductInfoRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductInfoRepositoryImpl implements ProductInfoRepository {
    private final JdbcTemplate jdbcTemplate;
    public ProductInfoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<ProductInfo> findExistingProductInfo(Long productId, Long cartId) {
        String sql = "select product_id, quantity, cart_id from product_info where product_id = ? and cart_id = ?";
        return jdbcTemplate.query(sql, new ProductInfoRowMapper(), productId, cartId).stream().findFirst();
    }

    @Override
    public void deleteProductInfo(Long productId, Long cartId) {
        String sql = "DELETE FROM product_info where product_id = ? and cart_id = ?";
        jdbcTemplate.update(sql, productId, cartId);
    }

    @Override
    public void updateProductInfoQuantity(ProductInfo existingProductInfo, ProductInfo productInfo) {
        String sql = "UPDATE product_info SET quantity = ? where product_id = ? and cart_id = ?";
        jdbcTemplate.update(sql, productInfo.getQuantity(), existingProductInfo.getProductId(), existingProductInfo.getCartId());
    }

    @Override
    public void createNewProductInfo(Product product, Cart cart, ProductInfo productInfo) {
        String sql = String.format("INSERT INTO product_info (product_id, quantity, cart_id) values (%s, %s, %s)", product.getProductId(), productInfo.getQuantity(), cart.getCartId());
        jdbcTemplate.update(sql);
    }

    @Override
    public List<ProductInfo> findAllProductInfoByCartId(Long cartId) {
        String sql = "select product_id, quantity, cart_id from product_info where cart_id = ?";
        return jdbcTemplate.query(sql, new ProductInfoRowMapper(), cartId);
    }

    @Override
    public Optional<ProductInfo> findProductInfoByProductId(Long productId) {
        String sql = "select product_id, quantity, cart_id from product_info where product_id = ?";
        return jdbcTemplate.query(sql, new ProductInfoRowMapper(), productId).stream().findFirst();
    }

    @Override
    public Optional<ProductInfo> saveProductInfo(ProductInfo productInfo, Product product, Cart cart) {
        String sql = "INSERT INTO product_info (product_id, quantity, cart_id) values (?, ?, ?)";
        jdbcTemplate.update(sql, product.getProductId(), productInfo.getQuantity(), cart.getCartId());
        return findExistingProductInfo(product.getProductId(), cart.getCartId());
    }

    @Override
    public void deleteProductInfosByCartId(Long cartId) {
        String sql = "DELETE FROM product_info where cart_id = ?";
        jdbcTemplate.update(sql, cartId);
    }
}
