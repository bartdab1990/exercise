package com.yapily.bart.exercise.mapper;

import com.yapily.bart.exercise.model.ProductInfo;
import com.yapily.bart.exercise.model.dto.ProductInfoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class ProductInfoMapper {

    private final ModelMapper modelMapper;

    public ProductInfoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductInfoDTO convertProductEntityToDTO(ProductInfo productInfo) {
        ProductInfoDTO productInfoDTO = new ProductInfoDTO();
        try {
            productInfoDTO = modelMapper.map(productInfo, ProductInfoDTO.class);
        } catch (Exception e) {
            if (nonNull(productInfo.getProductId())) {
                productInfoDTO.setProductId(productInfo.getProductId());
            }

            if (nonNull(productInfo.getQuantity())) {
                productInfoDTO.setQuantity(productInfo.getQuantity());
            }
        }
        return productInfoDTO;
    }
}
