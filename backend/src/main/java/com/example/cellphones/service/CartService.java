package com.example.cellphones.service;

import com.example.cellphones.dto.CartDetailDto;
import com.example.cellphones.dto.CartDto;
import com.example.cellphones.dto.request.cart.UpdateCartDetailReq;
import com.example.cellphones.dto.request.cart.UpdateQuantityCartDetailReq;

import java.util.List;

public interface CartService {
    List<CartDetailDto> getCart(Long userId);

    String addProductToCart(UpdateCartDetailReq req, Long userId);

    String removeProductFromCart(Long cartDetailId, Long userId);

    CartDetailDto updateQuantityCartDetail(UpdateQuantityCartDetailReq req);
}
