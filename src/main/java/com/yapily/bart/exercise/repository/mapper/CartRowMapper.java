package com.yapily.bart.exercise.repository.mapper;

import com.yapily.bart.exercise.model.Cart;
import com.yapily.bart.exercise.model.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CartRowMapper implements RowMapper<Cart> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Cart(rs.getLong("cart_id"),
                rs.getBoolean("checked_out"));
    }

    public List<Cart> createCartRelationships(ResultSet rs) throws SQLException {
        List<Cart> carts = new ArrayList<>();
        Long cartId = null;
        Cart cart = null;
        int cartx = 0;
        while (rs.next()) {
            if (cart == null || !cartId.equals(rs.getLong("cart_id"))) {
                cartId = rs.getLong("cart_id");
                cart = mapRow(rs, cartx++);
                carts.add(cart);

                String sql = "select product_id, cart_id, quantity from product_info WHERE cart_id = ?";
                List<ProductInfo> productInfoList = jdbcTemplate.query(sql, new ProductInfoRowMapper(), cartId);
                if (!productInfoList.isEmpty()) {
                    cart.setProducts(productInfoList);
                }
            }
        }
        return carts;
    }
}
