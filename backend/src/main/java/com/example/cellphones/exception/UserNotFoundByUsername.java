package com.example.cellphones.exception;

public class UserNotFoundByUsername extends RuntimeException{
    public UserNotFoundByUsername(String username) {
        super("User not found with username: "+username);
    }
}
