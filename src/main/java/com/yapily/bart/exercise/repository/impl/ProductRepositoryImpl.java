package com.yapily.bart.exercise.repository.impl;

import com.yapily.bart.exercise.model.Product;
import com.yapily.bart.exercise.repository.ProductRepository;
import com.yapily.bart.exercise.repository.mapper.ProductRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAllProduct() {
        String sql = "select product_id, name, price, added_at, labels from product";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    @Override
    public Optional<Product> findProductByProductId(Long productId) {
        String sql = "select product_id, name, price, added_at, labels from product where product_id = ?";
        return jdbcTemplate.query(sql, new ProductRowMapper(), productId).stream().findFirst();
    }

    @Override
    public Optional<Product> existingProductByName(String name) {
        String sql = "select product_id, name, price, added_at, labels from product where name = ?";
        return jdbcTemplate.query(sql, new ProductRowMapper(), name).stream().findFirst();
    }

    @Override
    public Optional<Product> createNewProduct(Product product, String labels) {
        String productName = product.getName().substring(0, 200);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String newSql = String.format("insert into product (name, price, added_at, labels) values ('%s', %s, %s, '%s')", productName, product.getPrice(), new Date(), labels);
        jdbcTemplate.update(connection -> connection.prepareStatement(newSql, new String[] {"product_id"}), keyHolder);
        product.setProductId(keyHolder.getKey().longValue());
        return Optional.of(product);
    }

    @Override
    public Optional<Product> existingProductByProductId(Long productId) {
        String sql = "select product_id, name, price, added_at, labels from product where product_id = ?";
        return jdbcTemplate.query(sql, new ProductRowMapper(), productId).stream().findFirst();
    }

    @Override
    public int deleteProductByProductId(Long productId) {
        String sql = "DELETE FROM product where product_id = ?";
        return jdbcTemplate.update(sql, productId);
    }
}
