package com.yapily.bart.exercise.mapper;

import com.yapily.bart.exercise.model.Cart;
import com.yapily.bart.exercise.model.dto.CartDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class CartMapper {

    private final ModelMapper modelMapper;
    private final ProductInfoMapper productInfoMapper;


    public CartMapper(ModelMapper modelMapper, ProductInfoMapper productInfoMapper) {
        this.modelMapper = modelMapper;
        this.productInfoMapper = productInfoMapper;
    }

    public CartDTO convertCartEntityToDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        try {
            cartDTO = modelMapper.map(cart, CartDTO.class);
        } catch (Exception e) {
            if (nonNull(cart.getCartId())) {
                cartDTO.setCartId(cart.getCartId());
            }

            if (nonNull(cart.getProducts())) {
                cartDTO.setProducts(cart.getProducts().stream().map(productInfoMapper::convertProductEntityToDTO).collect(Collectors.toList()));
            }

            cartDTO.setCheckedOut(cartDTO.isCheckedOut());
        }
        return cartDTO;
    }
}
