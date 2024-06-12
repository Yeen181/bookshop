package com.example.cellphones.exception;

public class OrderNotFoundByIdException extends RuntimeException{
    public OrderNotFoundByIdException(Long id) {
        super("Order not found by " + id);
    }
}
