package com.yapily.bart.exercise.mapper;

import com.yapily.bart.exercise.model.CheckoutCart;
import com.yapily.bart.exercise.model.dto.CheckoutCartDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class CheckoutCartMapper {

    private final ModelMapper modelMapper;
    private final CartMapper cartMapper;

    public CheckoutCartMapper(ModelMapper modelMapper, CartMapper cartMapper) {
        this.modelMapper = modelMapper;
        this.cartMapper = cartMapper;
    }

    public CheckoutCartDTO convertCheckoutCartEntityToDTO(CheckoutCart checkoutCart) {
        CheckoutCartDTO checkoutCartDTO = new CheckoutCartDTO();
        if (nonNull(checkoutCart.getCart())) {
            checkoutCartDTO.setCartDTO(cartMapper.convertCartEntityToDTO(checkoutCart.getCart()));
        }

        if (nonNull(checkoutCart.getTotalCost())) {
            checkoutCartDTO.setTotalCost(checkoutCart.getTotalCost());
        }
        return checkoutCartDTO;
    }
}
