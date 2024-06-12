package com.example.cellphones.mapper;

import com.example.cellphones.dto.CartDto;
import com.example.cellphones.model.Cart;

public class CartMapper {
    public static CartDto responseCartDtoFromModel(Cart cart){
        return CartDto.builder()
                .id(cart.getId())
                .listCartDetail(cart.getCartDetails())
                .build();
    }
}
