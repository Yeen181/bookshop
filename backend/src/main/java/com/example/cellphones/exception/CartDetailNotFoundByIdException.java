package com.example.cellphones.exception;

public class CartDetailNotFoundByIdException extends RuntimeException{
    public CartDetailNotFoundByIdException(Long id) {
        super("Cart detail not found by " + id);
    }
}
