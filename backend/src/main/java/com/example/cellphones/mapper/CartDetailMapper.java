package com.example.cellphones.mapper;

import com.example.cellphones.dto.CartDetailDto;
import com.example.cellphones.model.CartDetail;

public class CartDetailMapper {
    public static CartDetailDto responseCartDetailDtoFromModel(CartDetail cartDetail){
        return CartDetailDto.builder()
                .id(cartDetail.getId())
                .productDto(ProductMapper.responseProductDtoFromModel(cartDetail.getProduct()))
                .quantity(cartDetail.getQuantity())
                .build();
    }
}
