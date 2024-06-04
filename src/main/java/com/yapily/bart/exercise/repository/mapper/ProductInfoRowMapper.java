package com.yapily.bart.exercise.repository.mapper;

import com.yapily.bart.exercise.model.ProductInfo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductInfoRowMapper implements RowMapper<ProductInfo> {
    @Override
    public ProductInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ProductInfo(rs.getLong("product_id"),
                rs.getInt("quantity"),
                rs.getLong("cart_id"));
    }
}
