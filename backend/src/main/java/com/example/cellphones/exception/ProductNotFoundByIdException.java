package com.example.cellphones.exception;

public class ProductNotFoundByIdException extends RuntimeException{
    public ProductNotFoundByIdException(String message, Long id) {
        super(message + id);
    }

    public ProductNotFoundByIdException(Long id) {
        super("Product not found by " + id);
    }

}
