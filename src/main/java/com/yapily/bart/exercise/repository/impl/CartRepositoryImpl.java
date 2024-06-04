package com.yapily.bart.exercise.repository.impl;

import com.yapily.bart.exercise.model.Cart;
import com.yapily.bart.exercise.repository.CartRepository;
import com.yapily.bart.exercise.repository.mapper.CartRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Repository
public class CartRepositoryImpl implements CartRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CartRowMapper cartRowMapper;

    public CartRepositoryImpl(JdbcTemplate jdbcTemplate, CartRowMapper cartRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.cartRowMapper = cartRowMapper;
    }

    @Override
    public Optional<Cart> createNewCart() {
        Cart product = new Cart();
        product.setCheckedOut(false);
        product.setProducts(emptyList());
        String sql = "INSERT INTO CART (checked_out, total_cost) values (false, 0.0)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> connection.prepareStatement(sql, new String[] {"cart_id"}), keyHolder);
        product.setCartId(keyHolder.getKey().longValue());
        return Optional.of(product);
    }

    @Override
    public Optional<Cart> findCartByCartId(Long cartId) {
        String sql = "select cart_id, checked_out from cart where cart_id = ?";
        return jdbcTemplate.query(sql, new CartRowMapper(), cartId).stream().findFirst();
    }

    @Override
    public int startCheckout(Long cartId, boolean flag) {
        String sql = "UPDATE cart SET checked_out = ? where cart_id = ?";
        return jdbcTemplate.update(sql, flag, cartId);
    }

    @Override
    public double calculateTotalCostForCart(Long cartId) {
        try {
            String sql = "select sum(pi.quantity * p.price) from cart t INNER JOIN product_info pi ON t.cart_id = pi.cart_id " +
                    "INNER JOIN product p ON p.product_id = pi.product_id " +
                    "where t.cart_id = ?";
            return jdbcTemplate.queryForObject(sql, Double.class, cartId);
        } catch (NullPointerException e) {
            return 0.0;
        }
    }

    @Override
    public void updateTotalCost(double totalCost, Long cartId) {
        String sql = "UPDATE cart set total_cost = ? where cart_id = ?";
        jdbcTemplate.update(sql, totalCost, cartId);
    }

    @Override
    public List<Cart> findAllCarts() {
        String sql = "select cart_id, checked_out from cart";
        return jdbcTemplate.query(sql, cartRowMapper::createCartRelationships);
    }

    @Override
    public void revertCheckoutChanges(Long cartId) {
        String sql = "UPDATE cart set total_cost = 0.0, checked_out = false where cart_id = ?";
        jdbcTemplate.update(sql, cartId);
    }
}
