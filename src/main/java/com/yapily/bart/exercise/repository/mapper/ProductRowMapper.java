package com.yapily.bart.exercise.repository.mapper;

import com.yapily.bart.exercise.model.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Component
public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<String> labels = Arrays.stream(rs.getString("labels").split(",")).toList();
        return new Product(rs.getLong("product_id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getDate("added_at"),
                labels);
    }
}
