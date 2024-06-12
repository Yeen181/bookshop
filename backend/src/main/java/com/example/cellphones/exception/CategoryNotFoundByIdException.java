package com.example.cellphones.exception;

public class CategoryNotFoundByIdException extends RuntimeException{
    public CategoryNotFoundByIdException(Long id) {
        super("Category not found by " + id);
    }
}
