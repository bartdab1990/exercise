package com.yapily.bart.exercise.model;

import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public class Product {
    private Long productId;
    private String name;
    private double price;
    private Date addedAt;
    private List<String> labels;
    public Product() {
    }

    public Product(Long productId, String name, double price, Date addedAt, List<String> labels) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.addedAt = addedAt;
        this.labels = labels;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", addedAt=" + addedAt +
                ", labels=" + labels +
                '}';
    }
}
