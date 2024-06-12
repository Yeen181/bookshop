package com.example.cellphones.exception;

public class UserNotFoundByIdException extends RuntimeException{
    public UserNotFoundByIdException(Long id) {
        super("User not found by " + id);
    }
}
