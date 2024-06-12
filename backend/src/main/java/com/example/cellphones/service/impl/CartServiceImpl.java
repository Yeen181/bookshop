package com.example.cellphones.service.impl;

import com.example.cellphones.dto.CartDetailDto;
import com.example.cellphones.dto.request.cart.UpdateCartDetailReq;
import com.example.cellphones.dto.request.cart.UpdateQuantityCartDetailReq;
import com.example.cellphones.exception.CartDetailNotFoundByIdException;
import com.example.cellphones.exception.CustomException;
import com.example.cellphones.exception.ProductNotFoundByIdException;
import com.example.cellphones.mapper.CartDetailMapper;
import com.example.cellphones.mapper.ProductMapper;
import com.example.cellphones.model.Cart;
import com.example.cellphones.model.CartDetail;
import com.example.cellphones.model.Product;
import com.example.cellphones.repository.CartDetailRepository;
import com.example.cellphones.repository.CartRepository;
import com.example.cellphones.repository.ProductRepository;
import com.example.cellphones.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepo;

    private final ProductRepository productRepo;

    private final CartDetailRepository cartDetailRepo;


    @Override
    public List<CartDetailDto> getCart(Long userId) {
        Cart cart = this.cartRepo.findCartByUserId(userId);
        return cart.getCartDetails().stream()
                .map(c -> CartDetailDto.builder()
                        .id(c.getId())
                        .productDto(ProductMapper.responseProductDtoFromModel(c.getProduct()))
                        .quantity(c.getQuantity())
                        .build()
                ).toList();
    }

    @Override
    public String addProductToCart(UpdateCartDetailReq req, Long userId) {
        try {
            Cart cart = cartRepo.findCartByUserId(userId);
            List<CartDetail> listCartDetail = cart.getCartDetails();

            boolean productExists = listCartDetail.stream()
                    .anyMatch(cartDetail -> cartDetail.getProduct().getId().equals(req.getProductId()));
            if (productExists) {
                listCartDetail.forEach(cartDetail -> {
                    if (cartDetail.getProduct().getId().equals(req.getProductId())) {
                        cartDetail.setQuantity(cartDetail.getQuantity() + req.getQuantity());
                    }
                });
            } else {
                Product product = this.productRepo.findById(req.getProductId())
                        .orElseThrow(() -> new ProductNotFoundByIdException(req.getProductId()));
                listCartDetail.add(CartDetail.builder()
                        .cart(cart)
                        .product(product)
                        .quantity(req.getQuantity())
                        .build());
            }
            cart.setCartDetails(listCartDetail);
            this.cartRepo.save(cart);
            return "Successfully";
        } catch (Exception e) {
            throw new CustomException("Cannot add product to the cart");
        }
    }

    @Override
    public String removeProductFromCart(Long cartDetailId, Long userId) {
        try {
            CartDetail cartDetail = this.cartDetailRepo.findById(cartDetailId).orElseThrow(() -> new CartDetailNotFoundByIdException(cartDetailId));
            cartDetail.setCart(null);
            cartDetail.setProduct(null);
            this.cartDetailRepo.delete(cartDetail);
            return "Successfully";
        } catch (Exception e) {
            throw new CustomException("Cannot remove product from the cart");
        }

    }

    @Override
    public CartDetailDto updateQuantityCartDetail(UpdateQuantityCartDetailReq req) {
        try {
            CartDetail cartDetail = this.cartDetailRepo.findById(req.getCartDetailId())
                    .orElseThrow(() -> new CartDetailNotFoundByIdException(req.getCartDetailId()));
            cartDetail.setQuantity(req.getQuantity());
            this.cartDetailRepo.save(cartDetail);
            return (CartDetailMapper.responseCartDetailDtoFromModel(cartDetail));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
