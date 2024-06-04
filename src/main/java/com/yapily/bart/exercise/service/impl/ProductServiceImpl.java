package com.yapily.bart.exercise.service.impl;

import com.yapily.bart.exercise.Label;
import com.yapily.bart.exercise.model.Product;
import com.yapily.bart.exercise.repository.ProductRepository;
import com.yapily.bart.exercise.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAllProduct();
    }

    @Override
    public Optional<Product> findProductByProductId(Long productId) {
        return productRepository.findProductByProductId(productId);
    }

    @Override
    public Optional<Product> createNewProduct(Product product) {
        if (productRepository.existingProductByName(product.getName()).isPresent()) {
            return Optional.empty();
        } else {
            if (!product.getLabels().isEmpty()) {
                String labelString = convertListOfLabelsIntoString(product);
                //Create the object even if the label is incorrect
                return productRepository.createNewProduct(product, labelString);
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public int deleteProductByProductId(Long productId) {
        if (productRepository.existingProductByProductId(productId).isPresent()) {
            return productRepository.deleteProductByProductId(productId);
        }
        return 0;
    }

    private String convertListOfLabelsIntoString(Product product) {
        StringBuilder labelBuilder = new StringBuilder();
        List<String> labels = product.getLabels();
        for (int i = 0; i < labels.size(); i++) {
            for (Label staticLabel : Label.values()) {
                if (staticLabel.toString().equalsIgnoreCase(labels.get(i)) && i == labels.size() - 1) {
                    labelBuilder.append(labels.get(i));
                } else if (staticLabel.toString().equalsIgnoreCase(labels.get(i))) {
                    labelBuilder.append(labels.get(i));
                    labelBuilder.append(",");
                } else {
                    labels.remove(i);
                }
            }
        }
        product.setLabels(labels);
        return labelBuilder.toString();
    }
}
