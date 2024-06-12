package com.example.cellphones.controller;

import com.example.cellphones.dto.CartDetailDto;
import com.example.cellphones.dto.CartDto;
import com.example.cellphones.dto.request.cart.UpdateCartDetailReq;
import com.example.cellphones.dto.request.cart.UpdateQuantityCartDetailReq;
import com.example.cellphones.model.user.User;
import com.example.cellphones.response.ResponseObject;
import com.example.cellphones.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;


    @GetMapping(path = "/current")
    public ResponseEntity<?> getCart() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(cartService.getCart(currentUser.getId()))
                        .build()
        );
    }

    @PostMapping(path = "/add-product")
    public ResponseEntity<?> updateCartDetail(@RequestBody UpdateCartDetailReq req) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(cartService.addProductToCart(req, currentUser.getId()))
                        .build()
        );
    }

    @DeleteMapping(path = "/remove-product/{id}")
    public ResponseEntity<?> removeCartDetail(@PathVariable String id) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(cartService.removeProductFromCart(Long.parseLong(id), currentUser.getId()))
                        .build()
        );
    }

    @PostMapping(path = "/update-quantity")
    public ResponseEntity<?> updateQuantityCartDetail(@RequestBody UpdateQuantityCartDetailReq req) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(cartService.updateQuantityCartDetail(req))
                        .build()
        );
    }
}
