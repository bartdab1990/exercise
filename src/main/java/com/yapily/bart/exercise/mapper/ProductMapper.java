package com.yapily.bart.exercise.mapper;

import com.yapily.bart.exercise.model.Product;
import com.yapily.bart.exercise.model.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductDTO convertProductEntityToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        try {
            productDTO = modelMapper.map(product, ProductDTO.class);
        } catch (Exception e) {
            if (nonNull(product.getProductId())) {
                productDTO.setProductId(product.getProductId());
            }

            if (nonNull(product.getName())) {
                productDTO.setName(product.getName());
            }

            if (nonNull(product.getPrice())) {
                productDTO.setPrice(product.getPrice());
            }

            if (nonNull(product.getAddedAt())) {
                productDTO.setAddedAt(product.getAddedAt());
            }

            if (nonNull(product.getLabels())) {
                productDTO.setLabels(product.getLabels());
            }
        }
        return productDTO;
    }

    public Product convertProductDTOtoEntity(ProductDTO productDTO) {
        Product product = new Product();
        try {
            product = modelMapper.map(productDTO, Product.class);
        } catch (Exception e) {
            if (nonNull(productDTO.getProductId())) {
                product.setProductId(productDTO.getProductId());
            }

            if (nonNull(productDTO.getName())) {
                product.setName(productDTO.getName());
            }

            if (nonNull(productDTO.getPrice())) {
                product.setPrice(productDTO.getPrice());
            }

            if (nonNull(productDTO.getAddedAt())) {
                product.setAddedAt(productDTO.getAddedAt());
            }

            if (nonNull(productDTO.getLabels())) {
                product.setLabels(productDTO.getLabels());
            }
        }
        return product;
    }
}
